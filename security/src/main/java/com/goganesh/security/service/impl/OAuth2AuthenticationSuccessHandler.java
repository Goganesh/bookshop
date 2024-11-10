package com.goganesh.security.service.impl;

import com.goganesh.security.model.UserDetailsImpl;
import com.goganesh.security.service.CookieService;
import com.goganesh.security.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

@Service
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final String authTokenName;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final int cookieLifetimeDay;
    private final UserDetailsService userDetailsService;

    public OAuth2AuthenticationSuccessHandler(@Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                                              JwtService jwtService,
                                              CookieService cookieService,
                                              @Value("${com.goganesh.bookshop.cookie.lifetime-day}") int cookieLifetimeDay,
                                              UserDetailsService userDetailsService) {
        this.authTokenName = authTokenName;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
        this.cookieLifetimeDay = cookieLifetimeDay;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(user.getAttribute("email"));
        String jwt = jwtService.generateToken(userDetails.getUser().getHash());

        cookieService.addCookie(httpServletResponse, authTokenName, jwt, Duration.ofDays(cookieLifetimeDay));

        httpServletResponse.sendRedirect("/");
    }
}
