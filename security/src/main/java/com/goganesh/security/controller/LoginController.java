package com.goganesh.security.controller;

import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.service.UserContactReadRepository;
import com.goganesh.otp.service.OtpService;
import com.goganesh.security.controller.dto.LoginRequest;
import com.goganesh.security.controller.dto.LoginResponse;
import com.goganesh.security.controller.dto.SendOtpLoginRequest;
import com.goganesh.security.controller.dto.SendOtpLoginResponse;
import com.goganesh.security.service.CookieService;
import com.goganesh.security.service.LoginService;
import com.goganesh.security.service.PhoneNumberService;
import lombok.Builder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Duration;
import java.util.logging.Logger;

@RestController
@Builder
public class LoginController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final UserContactReadRepository userContactReadRepository;
    private final PhoneNumberService phoneNumberService;
    private final LoginService loginService;
    private final OtpService otpService;
    private final CookieService cookieService;
    private final String authTokenName;
    private final int cookieLifetimeDay;

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

        final UserContact.ContactType contactType = userContactReadRepository.defineContactType(payload.getType());
        final String contact = contactType == UserContact.ContactType.PHONE ?
                phoneNumberService.formatPhoneNumber(payload.getContact()) : payload.getContact();

        userContactReadRepository.getApprovedContact(contact, contactType)
                .ifPresentOrElse(
                        userContact -> {
                            otpService.sendOtp(userContact);
                            response.setResult("true");
                            },
                        () -> logger.info(String.format("Can not send login otp for not approved contact %s, - %s",contactType, contact))
                );

        return response;
    }

    /**
     * Login user, login types: OTP_EMAIL, OTP_PHONE, PASSWORD_EMAIL
     * After success login: temp_user data merges with real_user, temp_user blocks, add jwt token to cookie
     *
     * @param payload - dto contains full information for login
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
