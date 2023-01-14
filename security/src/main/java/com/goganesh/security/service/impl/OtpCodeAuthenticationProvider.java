package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.LoginAttempt;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.otp.service.OtpService;
import com.goganesh.security.model.OtpCodeAuthentication;
import com.goganesh.security.model.UserDetailsImpl;
import com.goganesh.security.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.logging.Logger;

import static com.goganesh.bookshop.model.domain.UserContact.ContactType.PHONE;

@AllArgsConstructor
public class OtpCodeAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final OtpService otpService;
    private final LoginAttemptService loginAttemptService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(OtpCodeAuthentication.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof OtpCodeAuthentication) {
            return processAuthentication((OtpCodeAuthentication) authentication);
        } else {
            return authentication;
        }
    }

    private OtpCodeAuthentication processAuthentication(OtpCodeAuthentication authentication) throws AuthenticationException {
        final String contact = authentication.getContact();
        final String code = authentication.getCredentials();
        final UserContact.ContactType contactType = authentication.getContactType();
        final UserDetailsImpl tempUserDetails = (UserDetailsImpl) authentication.getPrincipal();

        final User[] user = new User[1];
        otpService.verifyCode(contact, contactType, code).ifPresentOrElse(
                userContact -> user[0] = userContact.getUser(),
                () -> {
                    loginAttemptService.loginFailed(tempUserDetails.getUser(), contactType == PHONE ? LoginAttempt.LoginType.PHONE : LoginAttempt.LoginType.EMAIL);
                    logger.info(String.format("Login by otp code %s for contact %s %s failed", code, contactType, contact));
                    throw new BadCredentialsException("Login by otp code failed");
                }
        );

        UserDetails userDetails = new UserDetailsImpl(user[0]);

        if (userDetails.isEnabled()) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            OtpCodeAuthentication fullTokenAuthentication = new OtpCodeAuthentication(
                    contact,
                    contactType,
                    code,
                    authorities,
                    userDetails);

            fullTokenAuthentication.setAuthenticated(true);

            return fullTokenAuthentication;

        } else {
            throw new AuthenticationServiceException("User disabled");
        }
    }
}
