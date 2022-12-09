package com.goganesh.otp.service;

import com.goganesh.bookshop.model.domain.UserContact;

import java.util.Optional;

public interface OtpService {
    /**
     * Verify otp code. Return Optional, if code verified - contains object, else contains null
     *
     * @param contact - contact phonenumber or email
     * @param contactType - contact type: PHONE, EMAIL
     * @param code - otp code sent before
     * @return - return Optional, if code verified - contains object, else contains null
     */
    Optional<UserContact> verifyCode(String contact, UserContact.ContactType contactType, String code);
    
    void sendOtp(UserContact userContact);
}
