package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.InvalidToken;
import com.goganesh.bookshop.model.service.InvalidTokenWriteRepository;
import com.goganesh.security.exception.NoJwtTokenException;
import com.goganesh.security.service.CookieService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final String authTokenName;
    private final InvalidTokenWriteRepository invalidTokenWriteRepository;
    private final CookieService cookieService;

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        Cookie cookie = cookieService.getCookie(request, authTokenName).orElseThrow(() -> new NoJwtTokenException("There is no jwt token in cookies"));
        invalidTokenWriteRepository.save(InvalidToken.builder().token(cookie.getValue()).build());
    }
}
