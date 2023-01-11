package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.repository.JpaUserContactRepository;
import com.goganesh.bookshop.model.service.UserContactReadRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JpaUserContactReadRepository implements UserContactReadRepository {

    private final JpaUserContactRepository jpaUserContactRepository;

    @Override
    public Optional<UserContact> getApprovedEmail(User user) {
        return jpaUserContactRepository.findByContactTypeAndApprovedAndUser(UserContact.ContactType.EMAIL, true, user);
    }

    @Override
    public Optional<UserContact> getApprovedPhone(User user) {
        return jpaUserContactRepository.findByContactTypeAndApprovedAndUser(UserContact.ContactType.PHONE, true, user);
    }

    @Override
    public Optional<UserContact> getApprovedContact(String contact, UserContact.ContactType contactType) {
        return jpaUserContactRepository.findByContactAndContactTypeAndApproved(contact, contactType, true);
    }

    @Override
    public Optional<UserContact> getApprovedContact(String contact, UserContact.ContactType contactType, User user) {
        return jpaUserContactRepository.findByContactAndContactTypeAndApprovedAndUser(contact, contactType, true, user);
    }

    @Override
    public List<UserContact> getApprovedContacts(User user) {
        return jpaUserContactRepository.findByUserAndApproved(user, true);
    }

    @Override
    public Optional<UserContact> findByContactAndContactTypeAndCode(String contact, UserContact.ContactType contactType, String code) {
        return jpaUserContactRepository.findByContactAndContactTypeAndCode(contact, contactType, code);
    }

    @Override
    public UserContact.ContactType defineContactType(String type) {
        UserContact.ContactType contactType;

        if (type.equals("phone")) {
            contactType = UserContact.ContactType.PHONE;
        } else if (type.equals("mail")) {
            contactType = UserContact.ContactType.EMAIL;
        } else {
            throw new RuntimeException("sd"); //todo
        }

        return contactType;
    }
}
