package com.goganesh.security.configuration;

import com.goganesh.otp.configuration.OtpConfiguration;
import com.goganesh.security.service.*;
import com.goganesh.security.service.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@Import({OtpConfiguration.class})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final String authTokenName;
    private final CustomLogoutHandler logoutHandler;
    private final JwtTokenAuthenticationProvider jwtTokenAuthenticationProvider;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2UserService oAuth2UserService;
    private final OtpCodeAuthenticationProvider otpCodeAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final UserRegisterService userRegisterService;
    private final int cookieLifetimeDay;
    private final CookieService cookieService;
    private final PhoneNumberService phoneNumberService;

    public SecurityConfiguration(UserDetailsService userDetailsService,
                                 @Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                                 CustomLogoutHandler logoutHandler,
                                 JwtTokenAuthenticationProvider jwtTokenAuthenticationProvider,
                                 OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                                 OAuth2UserService oAuth2UserService,
                                 OtpCodeAuthenticationProvider otpCodeAuthenticationProvider,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
                                 UserRegisterService userRegisterService,
                                 @Value("${com.goganesh.bookshop.cookie.lifetime-day}") int cookieLifetimeDay,
                                 CookieService cookieService,
                                 PhoneNumberService phoneNumberService

    ) {
        this.userDetailsService = userDetailsService;
        this.authTokenName = authTokenName;
        this.logoutHandler = logoutHandler;
        this.jwtTokenAuthenticationProvider = jwtTokenAuthenticationProvider;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2UserService = oAuth2UserService;
        this.otpCodeAuthenticationProvider = otpCodeAuthenticationProvider;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
        this.userRegisterService = userRegisterService;
        this.cookieLifetimeDay = cookieLifetimeDay;
        this.cookieService = cookieService;
        this.phoneNumberService = phoneNumberService;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() throws Exception {
        return JwtAuthenticationProcessingFilter.builder()
                .authTokenName(authTokenName)
                .userDetailsService(userDetailsService)
                .jwtService(jwtService)
                .jwtAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler)
                .authenticationManager(authenticationManagerBean())
                .userRegisterService(userRegisterService)
                .cookieLifetimeDay(cookieLifetimeDay)
                .cookieService(cookieService)
                .build();
    }

    @Bean
    public LoginService loginService() throws Exception {
        return LoginServiceImpl.builder()
                .phoneNumberService(phoneNumberService)
                .jwtService(jwtService)
                .authenticationManager(authenticationManagerBean())
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(jwtTokenAuthenticationProvider)
                .authenticationProvider(otpCodeAuthenticationProvider)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable();

        http
                .authorizeRequests()
                .antMatchers("/my", "/myarchive", "/profile", "/viewed").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/signin")
                .failureUrl("/signin")
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessUrl("/signin")
                .deleteCookies(authTokenName);
        http
                .addFilterBefore(jwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .oauth2Login(config -> {
                    config.userInfoEndpoint(subconfig -> subconfig.userService(oAuth2UserService));
                    config.successHandler(oAuth2AuthenticationSuccessHandler);
                });
    }

}
