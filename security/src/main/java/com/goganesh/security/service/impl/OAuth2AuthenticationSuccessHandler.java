package com.goganesh.security.service.impl;

import com.goganesh.security.model.UserDetailsImpl;
import com.goganesh.security.service.CookieService;
import com.goganesh.security.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

@AllArgsConstructor
@Builder
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final String authTokenName;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final int cookieLifetimeDay;
    private final UserDetailsService userDetailsService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(user.getAttribute("email"));
        String jwt = jwtService.generateToken(userDetails.getUser().getHash());

        cookieService.addCookie(httpServletResponse, authTokenName, jwt, Duration.ofDays(cookieLifetimeDay));

        httpServletResponse.sendRedirect("/");
    }
}
