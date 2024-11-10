package com.goganesh.security.controller;

import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.repository.UserContactRepository;
import com.goganesh.otp.service.OtpService;
import com.goganesh.security.controller.dto.LoginRequest;
import com.goganesh.security.controller.dto.LoginResponse;
import com.goganesh.security.controller.dto.SendOtpLoginRequest;
import com.goganesh.security.controller.dto.SendOtpLoginResponse;
import com.goganesh.security.service.CookieService;
import com.goganesh.security.service.LoginService;
import com.goganesh.security.service.PhoneNumberService;
import com.goganesh.security.service.UserContactService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.Duration;
import java.util.logging.Logger;

@RestController
public class LoginController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final UserContactRepository userContactRepository;
    private final UserContactService userContactService;
    private final PhoneNumberService phoneNumberService;
    private final LoginService loginService;
    private final OtpService otpService;
    private final CookieService cookieService;
    private final String authTokenName;
    private final int cookieLifetimeDay;

    public LoginController(UserContactRepository userContactRepository,
                           UserContactService userContactService, PhoneNumberService phoneNumberService,
                           LoginService loginService,
                           OtpService otpService,
                           CookieService cookieService,
                           @Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                           @Value("${com.goganesh.bookshop.cookie.lifetime-day}") int cookieLifetimeDay) {
        this.userContactRepository = userContactRepository;
        this.userContactService = userContactService;
        this.phoneNumberService = phoneNumberService;
        this.loginService = loginService;
        this.otpService = otpService;
        this.cookieService = cookieService;
        this.authTokenName = authTokenName;
        this.cookieLifetimeDay = cookieLifetimeDay;
    }

    /**
     * Send otp to approved contact for login. Need for login for types: OTP_EMAIL, OTP_PHONE
     *
     * @param payload - dto contains full information for send otp
     * @return - result
     */
    @PreAuthorize("hasRole('TEMP_USER')")
    @PostMapping("/send-login-otp")
    public SendOtpLoginResponse sendLoginOtp(@Valid @RequestBody SendOtpLoginRequest payload) {
        final SendOtpLoginResponse response = SendOtpLoginResponse.builder()
                .result("false")
                .build();

        final UserContact.ContactType contactType = userContactService.defineContactType(payload.getType());
        final String contact = contactType == UserContact.ContactType.PHONE ?
                phoneNumberService.formatPhoneNumber(payload.getContact()) : payload.getContact();

        userContactRepository.findByContactAndContactTypeAndApproved(contact, contactType, true)
                .ifPresentOrElse(
                        userContact -> {
                            otpService.sendOtp(userContact);
                            response.setResult("true");
                        },
                        () -> logger.info(String.format("Can not send login otp for not approved contact %s, - %s", contactType, contact))
                );

        return response;
    }

    /**
     * Login user, login types: OTP_EMAIL, OTP_PHONE, PASSWORD_EMAIL
     * After success login: temp_user data merges with real_user, temp_user blocks, add jwt token to cookie
     *
     * @param payload  - dto contains full information for login
     * @param response - http response, need for inject token for auth user
     * @return - result
     */
    @PreAuthorize("hasRole('TEMP_USER')")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest payload,
                               HttpServletResponse response) {
        String jwtToken = loginService.login(payload);
        cookieService.addCookie(response, authTokenName, jwtToken, Duration.ofDays(cookieLifetimeDay));

        return LoginResponse.builder()
                .result("true")
                .build();
    }
}
