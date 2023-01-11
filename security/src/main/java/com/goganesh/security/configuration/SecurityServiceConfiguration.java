package com.goganesh.security.configuration;

import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.bookshop.model.service.*;
import com.goganesh.otp.configuration.OtpConfiguration;
import com.goganesh.otp.service.OtpService;
import com.goganesh.security.controller.ContactApproveController;
import com.goganesh.security.controller.LoginController;
import com.goganesh.security.controller.ProfileController;
import com.goganesh.security.controller.RegisterUserController;
import com.goganesh.security.service.*;
import com.goganesh.security.service.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import({ModelConfiguration.class, OtpConfiguration.class})
public class SecurityServiceConfiguration {

    @Bean
    public CustomLogoutHandler customLogoutHandler(@Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                                                   InvalidTokenWriteRepository invalidTokenWriteRepository,
                                                   CookieService cookieService) {
        return new CustomLogoutHandler(authTokenName, invalidTokenWriteRepository, cookieService);
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService(UserReadRepository userReadRepository,
                                                           UserWriteRepository userWriteRepository,
                                                           UserContactWriteRepository userContactWriteRepository) {
        return new CustomOAuth2UserService(userReadRepository, userWriteRepository, userContactWriteRepository);
    }


    @Bean
    public LoginAttemptService loginAttemptService(@Value("${com.goganesh.bookshop.login-attempt.max-attempt}") int maxAttempt,
                                                   @Value("${com.goganesh.bookshop.login-attempt.block-time-minutes}") int blockTimeMinutes,
                                                   LoginAttemptReadRepository loginAttemptReadRepository,
                                                   LoginAttemptWriteRepository loginAttemptWriteRepository) {
        return new LoginAttemptServiceImpl(maxAttempt, blockTimeMinutes, loginAttemptReadRepository, loginAttemptWriteRepository);
    }

    @Bean
    public OtpCodeAuthenticationProvider otpCodeAuthenticationProvider(OtpService otpService,
                                                                       LoginAttemptService loginAttemptService) {
        return new OtpCodeAuthenticationProvider(otpService, loginAttemptService);
    }

    @Bean
    public JwtTokenAuthenticationProvider jwtTokenAuthenticationProvider(InvalidTokenReadRepository invalidTokenReadRepository,
                                                                         JwtService jwtService,
                                                                         UserReadRepository userReadRepository) {
        return new JwtTokenAuthenticationProvider(invalidTokenReadRepository, jwtService, userReadRepository);
    }

    @Bean
    public UserDetailsService userDetailsService(UserReadRepository userReadRepository) {
        return new UserDetailsServiceImpl(userReadRepository);
    }

    @Bean
    public CookieService cookieService() {
        return new CookieServiceImpl();
    }

    @Bean
    public PhoneNumberServiceImpl phoneNumberService() {
        return new PhoneNumberServiceImpl();
    }

    @Bean
    public JwtService jwtService(@Value("${com.goganesh.bookshop.auth.token.secret}") String secret,
                                 @Value("${com.goganesh.bookshop.auth.token.lifetime}") int lifetime) {
        return new JwtServiceImpl(secret, lifetime);
    }

    @Bean
    public UserRegisterService userRegisterService(JwtService jwtService,
                                                   PhoneNumberService phoneNumberService,
                                                   Book2UserReadRepository book2UserReadRepository,
                                                   Book2UserWriteRepository book2UserWriteRepository,
                                                   UserReadRepository userReadRepository,
                                                   UserWriteRepository userWriteRepository,
                                                   UserContactReadRepository userContactReadRepository,
                                                   UserContactWriteRepository userContactWriteRepository) {
        return UserRegisterServiceImpl.builder()
                .jwtService(jwtService)
                .phoneNumberService(phoneNumberService)
                .book2UserReadRepository(book2UserReadRepository)
                .book2UserWriteRepository(book2UserWriteRepository)
                .userReadRepository(userReadRepository)
                .userWriteRepository(userWriteRepository)
                .userContactReadRepository(userContactReadRepository)
                .userContactWriteRepository(userContactWriteRepository)
                .build();
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler(@Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                                                                                 JwtService jwtService,
                                                                                 CookieService cookieService,
                                                                                 @Value("${com.goganesh.bookshop.cookie.lifetime-day}") int cookieLifetimeDay,
                                                                                 UserDetailsService userDetailsService) {
        return OAuth2AuthenticationSuccessHandler.builder()
                .authTokenName(authTokenName)
                .cookieLifetimeDay(cookieLifetimeDay)
                .cookieService(cookieService)
                .jwtService(jwtService)
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler() {
        return new JwtAuthenticationSuccessHandler();
    }

    @Bean
    public LoginController loginController(UserContactReadRepository userContactReadRepository,
                                           PhoneNumberService phoneNumberService,
                                           LoginService loginService,
                                           OtpService otpService,
                                           CookieService cookieService,
                                           @Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                                           @Value("${com.goganesh.bookshop.cookie.lifetime-day}") int cookieLifetimeDay) {
        return LoginController.builder()
                .userContactReadRepository(userContactReadRepository)
                .phoneNumberService(phoneNumberService)
                .loginService(loginService)
                .otpService(otpService)
                .cookieService(cookieService)
                .authTokenName(authTokenName)
                .cookieLifetimeDay(cookieLifetimeDay)
                .build();
    }

    @Bean
    public RegisterUserController registerUserController(UserRegisterService userRegisterService,
                                                         CookieService cookieService,
                                                         @Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                                                         @Value("${com.goganesh.bookshop.cookie.lifetime-day}") int cookieLifetimeDay) {
        return RegisterUserController.builder()
                .userRegisterService(userRegisterService)
                .authTokenName(authTokenName)
                .cookieLifetimeDay(cookieLifetimeDay)
                .cookieService(cookieService)
                .build();
    }

    @Bean
    public ContactApproveController contactApproveController(UserRegisterService userRegisterService,
                                                             OtpService otpService,
                                                             UserContactReadRepository userContactReadRepository,
                                                             UserContactWriteRepository userContactWriteRepository,
                                                             PhoneNumberService phoneNumberService) {
        return ContactApproveController.builder()
                .userRegisterService(userRegisterService)
                .otpService(otpService)
                .userContactReadRepository(userContactReadRepository)
                .userContactWriteRepository(userContactWriteRepository)
                .phoneNumberService(phoneNumberService)
                .build();
    }

    @Bean
    public ProfileController profileController(UserWriteRepository userWriteRepository,
                                               UserRegisterService userRegisterService) {
        return ProfileController.builder()
                .userRegisterService(userRegisterService)
                .userWriteRepository(userWriteRepository)
                .build();
    }

}
