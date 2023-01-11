package com.goganesh.bookshop.model.service;


import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;

import java.util.List;
import java.util.Optional;

public interface UserContactReadRepository {


    UserContact.ContactType defineContactType(String type);

    Optional<UserContact> findByContactAndContactTypeAndCode(String contact, UserContact.ContactType contactType, String code);

    Optional<UserContact> getApprovedContact(String contact, UserContact.ContactType contactType);

    Optional<UserContact> getApprovedContact(String contact, UserContact.ContactType contactType, User user);

    Optional<UserContact> getApprovedEmail(User user);

    Optional<UserContact> getApprovedPhone(User user);

    List<UserContact> getApprovedContacts(User user);

}
