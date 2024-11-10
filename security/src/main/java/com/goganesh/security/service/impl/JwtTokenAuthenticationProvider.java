package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.InvalidTokenRepository;
import com.goganesh.bookshop.model.repository.UserRepository;
import com.goganesh.security.model.JwtTokenAuthentication;
import com.goganesh.security.model.UserDetailsImpl;
import com.goganesh.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {
    private final InvalidTokenRepository invalidTokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtTokenAuthenticationProvider(InvalidTokenRepository invalidTokenRepository,
                                          JwtService jwtService,
                                          UserRepository userRepository) {
        this.invalidTokenRepository = invalidTokenRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JwtTokenAuthentication.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtTokenAuthentication) {
            return processAuthentication((JwtTokenAuthentication) authentication);
        } else {
            return authentication;
        }
    }

    private JwtTokenAuthentication processAuthentication(JwtTokenAuthentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials();
        UserDetails userDetails;
        String username;

        if (invalidTokenRepository.findByToken(token).isPresent()) {
            throw new AuthenticationServiceException("Token is in blacklist - " + token);
        }
        try {
            username = jwtService.extractUsername(token);
            User user = userRepository.findByHash(username).orElseThrow(() -> new UsernameNotFoundException("user not found by hash - " + username));
            userDetails = new UserDetailsImpl(user);
        } catch (UsernameNotFoundException | ExpiredJwtException | SignatureException e) {
            throw new AuthenticationServiceException("Token is invalid - " + token);
        }

        if (userDetails.isEnabled()) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            JwtTokenAuthentication fullTokenAuthentication = new JwtTokenAuthentication(
                    authentication.getCredentials(),
                    authorities,
                    userDetails);
            fullTokenAuthentication.setDetails(authentication.getDetails());
            fullTokenAuthentication.setAuthenticated(true);
            return fullTokenAuthentication;

        } else {
            throw new AuthenticationServiceException("User disabled");
        }
    }
}
