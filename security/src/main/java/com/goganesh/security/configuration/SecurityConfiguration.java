package com.goganesh.security.configuration;

import com.goganesh.otp.OtpConfiguration;
import com.goganesh.security.service.*;
import com.goganesh.security.service.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@Import({OtpConfiguration.class})
public class SecurityConfiguration {

    private static final String LOGIN_RESOURCE = "/signin";
    private static final String LOGOUT_RESOURCE = "/logout";

    private final String authTokenName;
    private final CustomLogoutHandler logoutHandler;
    private final JwtTokenAuthenticationProvider jwtTokenAuthenticationProvider;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2UserService oAuth2UserService;
    private final OtpCodeAuthenticationProvider otpCodeAuthenticationProvider;
    private final JwtService jwtService;
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final UserRegisterService userRegisterService;
    private final int cookieLifetimeDay;
    private final CookieService cookieService;
    private final PhoneNumberService phoneNumberService;

    public SecurityConfiguration(@Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                                 CustomLogoutHandler logoutHandler,
                                 JwtTokenAuthenticationProvider jwtTokenAuthenticationProvider,
                                 OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                                 OAuth2UserService oAuth2UserService,
                                 OtpCodeAuthenticationProvider otpCodeAuthenticationProvider,
                                 JwtService jwtService,
                                 JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
                                 UserRegisterService userRegisterService,
                                 @Value("${com.goganesh.bookshop.cookie.lifetime-day}") int cookieLifetimeDay,
                                 CookieService cookieService,
                                 PhoneNumberService phoneNumberService

    ) {
        this.authTokenName = authTokenName;
        this.logoutHandler = logoutHandler;
        this.jwtTokenAuthenticationProvider = jwtTokenAuthenticationProvider;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2UserService = oAuth2UserService;
        this.otpCodeAuthenticationProvider = otpCodeAuthenticationProvider;
        this.jwtService = jwtService;
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
        this.userRegisterService = userRegisterService;
        this.cookieLifetimeDay = cookieLifetimeDay;
        this.cookieService = cookieService;
        this.phoneNumberService = phoneNumberService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginService loginService(AuthenticationManager authenticationManager) {
        return LoginServiceImpl.builder()
                .phoneNumberService(phoneNumberService)
                .jwtService(jwtService)
                .authenticationManager(authenticationManager)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(jwtTokenAuthenticationProvider, otpCodeAuthenticationProvider));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        var jwtAuthenticationProcessingFilter = JwtAuthenticationProcessingFilter.builder()
                .authTokenName(authTokenName)
                .jwtService(jwtService)
                .jwtAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler)
                .authenticationManager(authenticationManager)
                .userRegisterService(userRegisterService)
                .cookieLifetimeDay(cookieLifetimeDay)
                .cookieService(cookieService)
                .build();

        return http
                .csrf(AbstractHttpConfigurer::disable)
                //.cors(CorsConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/my", "/myarchive", "/profile", "/viewed").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/**").permitAll())
                .formLogin((loginForm) -> {
                    loginForm.loginPage(LOGIN_RESOURCE);
                    loginForm.failureForwardUrl(LOGIN_RESOURCE);
                })
                .logout((logout) -> {
                    logout.logoutUrl(LOGOUT_RESOURCE);
                    logout.addLogoutHandler(logoutHandler);
                    logout.logoutSuccessUrl(LOGIN_RESOURCE);
                    logout.deleteCookies(authTokenName);
                })
                .addFilterBefore(jwtAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login((oauth2Login) -> {
                    oauth2Login.userInfoEndpoint(subconfig -> subconfig.userService(oAuth2UserService));
                    oauth2Login.successHandler(oAuth2AuthenticationSuccessHandler);
                })
                .build();
    }

}
