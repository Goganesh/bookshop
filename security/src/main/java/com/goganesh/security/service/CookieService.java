package com.goganesh.security.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Optional;

public interface CookieService {

    Optional<Cookie> getCookie(HttpServletRequest request, String name);

    void addCookie(HttpServletResponse response, String name, String value, Duration maxAge);

    void deleteCookie(HttpServletResponse response, String name);

    String serialize(Object object);

    <T> T deserialize(Cookie cookie, Class<T> cls);

}
