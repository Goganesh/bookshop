package com.goganesh.security.service.impl;

import com.goganesh.security.service.CookieService;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.Optional;

public class CookieServiceImpl implements CookieService {

    private static final Boolean HTTP_ONLY = Boolean.TRUE;
    private static final Boolean SECURE = Boolean.FALSE;
    private static final String PATH = "/";

    @Override
    public Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public void addCookie (HttpServletResponse response, String name, String value, Duration maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(PATH);
        cookie.setHttpOnly(HTTP_ONLY);
        cookie.setSecure(SECURE);
        cookie.setMaxAge((int) maxAge.toSeconds());

        Rfc6265CookieProcessor processor = new Rfc6265CookieProcessor();
        String cookieString = processor.generateHeader(cookie);

        response.addHeader(HttpHeaders.SET_COOKIE, cookieString);
    }

    @Override
    public void deleteCookie(HttpServletResponse response, String name) {
        addCookie(response, name, "-", Duration.ZERO);
    }

    @Override
    public String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    @Override
    public <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())));
    }


}
