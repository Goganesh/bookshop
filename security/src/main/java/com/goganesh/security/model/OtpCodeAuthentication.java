package com.goganesh.security.model;

import com.goganesh.bookshop.model.domain.UserContact;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class OtpCodeAuthentication extends AbstractAuthenticationToken {

    private final String contact;
    private final UserContact.ContactType contactType;
    private final String credentials;
    private final Object principal;

    public OtpCodeAuthentication(String contact,
                                 UserContact.ContactType contactType,
                                 String code,
                                 Object principal) {

        super(null);
        super.setAuthenticated(false);
        this.contact = contact;
        this.contactType = contactType;
        this.credentials = code;
        this.principal = principal;
    }

    public OtpCodeAuthentication(String contact,
                                 UserContact.ContactType contactType,
                                 String code,
                                 Collection<? extends GrantedAuthority> authorities,
                                 Object principal) {

        super(authorities);
        super.setAuthenticated(false);
        this.contact = contact;
        this.contactType = contactType;
        this.credentials = code;
        this.principal = principal;
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
