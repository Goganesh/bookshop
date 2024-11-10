package com.goganesh.security.service;

import com.goganesh.bookshop.model.domain.UserContact;

public interface UserContactService {

    UserContact.ContactType defineContactType(String type);
}
