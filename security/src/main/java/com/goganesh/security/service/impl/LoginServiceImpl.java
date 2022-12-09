package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.security.controller.dto.LoginRequest;
import com.goganesh.security.model.OtpCodeAuthentication;
import com.goganesh.security.model.UserDetailsImpl;
import com.goganesh.security.service.JwtService;
import com.goganesh.security.service.LoginService;
import com.goganesh.security.service.PhoneNumberService;
import com.goganesh.security.service.UserRegisterService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@AllArgsConstructor
@Builder
public class LoginServiceImpl implements LoginService {

    private final UserRegisterService userRegisterService;
    private final AuthenticationManager AuthenticationManager;
    private final JwtService jwtService;
    private final PhoneNumberService phoneNumberService;

    @Override
    public String login(LoginRequest payload) {
        Authentication authentication;

        String code = payload.getCode().replace(" ", ""); //todo
        String contact = payload.getContact();
        final UserDetailsImpl tempUserDetails = new UserDetailsImpl(userRegisterService.getCurrentUser());

        switch (payload.getType()) {
            case "OTP_EMAIL":
                authentication = new OtpCodeAuthentication(contact, UserContact.ContactType.EMAIL, code, tempUserDetails);
                break;
            case "OTP_PHONE":
                contact = phoneNumberService.formatPhoneNumber(contact);
                authentication = new OtpCodeAuthentication(contact, UserContact.ContactType.PHONE, code, tempUserDetails);
                break;
            case "PASSWORD_EMAIL":
                authentication = new UsernamePasswordAuthenticationToken(contact, code);
                break;
            default:
                throw new RuntimeException("ddf");//todo
        }

        authentication = AuthenticationManager.authenticate(authentication);

        UserDetailsImpl realUserDetails = (UserDetailsImpl) authentication.getPrincipal();

        userRegisterService.blockTempUser(tempUserDetails.getUser(), realUserDetails.getUser());

        return jwtService.generateToken(realUserDetails.getUser().getHash());
    }
}
