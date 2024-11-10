package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.security.service.UserContactService;
import org.springframework.stereotype.Service;

@Service
public class UserContactServiceImpl implements UserContactService {

    @Override
    public UserContact.ContactType defineContactType(String type) {
        UserContact.ContactType contactType;

        if (type.equals("phone")) {
            contactType = UserContact.ContactType.PHONE;
        } else if (type.equals("mail")) {
            contactType = UserContact.ContactType.EMAIL;
        } else {
            throw new IllegalArgumentException("Can`t define contact type for " + type);
        }

        return contactType;
    }
}
