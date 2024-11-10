package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.InvalidToken;
import com.goganesh.bookshop.model.repository.InvalidTokenRepository;
import com.goganesh.security.exception.NoJwtTokenException;
import com.goganesh.security.service.CookieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class CustomLogoutHandler implements LogoutHandler {

    private final String authTokenName;
    private final InvalidTokenRepository invalidTokenRepository;
    private final CookieService cookieService;

    public CustomLogoutHandler(@Value("${com.goganesh.bookshop.auth.token.name}") String authTokenName,
                               InvalidTokenRepository invalidTokenRepository,
                               CookieService cookieService) {
        this.authTokenName = authTokenName;
        this.invalidTokenRepository = invalidTokenRepository;
        this.cookieService = cookieService;
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        Cookie cookie = cookieService.getCookie(request, authTokenName).orElseThrow(() -> new NoJwtTokenException("There is no jwt token in cookies"));
        invalidTokenRepository.save(InvalidToken.builder().token(cookie.getValue()).build());
    }
}
