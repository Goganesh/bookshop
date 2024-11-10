package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.security.model.JwtTokenAuthentication;
import com.goganesh.security.model.UserDetailsImpl;
import com.goganesh.security.service.CookieService;
import com.goganesh.security.service.JwtService;
import com.goganesh.security.service.UserRegisterService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.Builder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

public class JwtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public static final String DEFAULT_FILTER_PROCESSES_URI = "/**";
    private final String authTokenName;
    private final JwtService jwtService;
    private final UserRegisterService userRegisterService;
    private final CookieService cookieService;
    private final int cookieLifetimeDay;

    @Builder
    public JwtAuthenticationProcessingFilter(String authTokenName,
                                             JwtService jwtService,
                                             JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
                                             AuthenticationManager authenticationManager,
                                             UserRegisterService userRegisterService,
                                             CookieService cookieService,
                                             int cookieLifetimeDay) {
        super(DEFAULT_FILTER_PROCESSES_URI);
        this.authTokenName = authTokenName;
        this.jwtService = jwtService;
        this.userRegisterService = userRegisterService;
        this.cookieService = cookieService;
        this.cookieLifetimeDay = cookieLifetimeDay;

        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    private Optional<String> getTokenByRequest(HttpServletRequest request) {

        String jwtToken = null;

        Optional<Cookie> cookie = cookieService.getCookie(request, authTokenName);
        if (cookie.isPresent()) {
            jwtToken = cookie.get().getValue();
        }

        return Optional.ofNullable(jwtToken);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        JwtTokenAuthentication jwtTokenAuthentication;
        UserDetailsImpl userDetails;

        Optional<String> jwtToken = getTokenByRequest(request);

        if (jwtToken.isPresent()) {
            try {
                String usernameHash = jwtService.extractUsername(jwtToken.get());
                User user = userRegisterService.getUserByHash(usernameHash)
                        .orElseThrow(() -> new UsernameNotFoundException("No such user with username - " + usernameHash));
                userDetails = new UserDetailsImpl(user);
                jwtTokenAuthentication = new JwtTokenAuthentication(jwtToken.get(), userDetails);
            } catch (UsernameNotFoundException | ExpiredJwtException | SignatureException e) {
                jwtTokenAuthentication = createTempUser(response);
            }

        } else {
            jwtTokenAuthentication = createTempUser(response);
        }

        jwtTokenAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        jwtTokenAuthentication = (JwtTokenAuthentication) getAuthenticationManager().authenticate(jwtTokenAuthentication);

        return jwtTokenAuthentication;
    }

    private JwtTokenAuthentication createTempUser(HttpServletResponse response) {
        JwtTokenAuthentication jwtTokenAuthentication;
        String tempUserMail = "temp_" + UUID.randomUUID() + "@test.com";
        User user = userRegisterService.registerTempUser(tempUserMail);

        UserDetails userDetails = new UserDetailsImpl(user);
        String newJwtToken = jwtService.generateToken(user.getHash());

        jwtTokenAuthentication = new JwtTokenAuthentication(newJwtToken, userDetails);
        cookieService.addCookie(response, authTokenName, newJwtToken, Duration.ofDays(cookieLifetimeDay));

        return jwtTokenAuthentication;
    }
}
