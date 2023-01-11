package com.goganesh.security.service;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Optional;

public interface CookieService {

    Optional<Cookie> getCookie(HttpServletRequest request, String name);

    void addCookie(HttpServletResponse response, String name, String value, Duration maxAge);

    void deleteCookie(HttpServletResponse response, String name);

    String serialize(Object object);

    <T> T deserialize(Cookie cookie, Class<T> cls);

}
