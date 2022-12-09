package com.goganesh.security.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class JwtTokenAuthentication extends AbstractAuthenticationToken {
    private final String credentials;
    private final Object principal;

    public JwtTokenAuthentication(String token, Object principal) {
        super(null);
        this.principal = principal;
        this.credentials = token;
        this.setAuthenticated(false);
    }

    public JwtTokenAuthentication(String token,
                                  Collection<? extends GrantedAuthority> authorities,
                                  Object principal) {
        super(authorities);
        this.principal = principal;
        this.credentials = token;
        super.setAuthenticated(false);
    }

    @Override
    public String getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
