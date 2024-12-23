package com.goganesh.security.controller;

import com.goganesh.security.controller.dto.RegistrationFormRequest;
import com.goganesh.security.service.CookieService;
import com.goganesh.security.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;

@RestController
public class RegisterUserController {

    private final UserRegisterService userRegisterService;
    private final CookieService cookieService;
    private final String authTokenName;
    private final int cookieLifetimeDay;

    public RegisterUserController(UserRegisterService userRegisterService,
                                  CookieService cookieService,
                                  @Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                                  @Value("${com.goganesh.bookshop.cookie.lifetime-day}") int cookieLifetimeDay) {
        this.userRegisterService = userRegisterService;
        this.cookieService = cookieService;
        this.authTokenName = authTokenName;
        this.cookieLifetimeDay = cookieLifetimeDay;
    }

    /**
     * Register new user. Registration success if temp_user has approved contacts.
     * After success registration: temp_user data merges with real_user, temp_user blocks, add jwt token to cookie
     *
     * @param response         - http response, need for inject token for auth user
     * @param registrationForm - dto contains full information for approve otp code
     * @return - redirect to main page
     */
    @PreAuthorize("hasRole('TEMP_USER')")
    @PostMapping("/reg")
    public RedirectView userRegistration(RegistrationFormRequest registrationForm,
                                         HttpServletResponse response) {
        String jwtToken = userRegisterService.registerNewUser(registrationForm);
        cookieService.addCookie(response, authTokenName, jwtToken, Duration.ofDays(cookieLifetimeDay));

        return new RedirectView("/");
    }
}
