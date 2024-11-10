package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserContactRepository extends JpaRepository<UserContact, Integer> {
    Optional<UserContact> findByContactAndContactTypeAndApproved(String contact, UserContact.ContactType contactType, boolean approved);

    Optional<UserContact> findByContactAndContactTypeAndCode(String contact, UserContact.ContactType contactType, String code);

    List<UserContact> findByUser(User user);

    Optional<UserContact> findByContactAndContactTypeAndApprovedAndUser(String contact, UserContact.ContactType contactType, boolean approved, User user);

    Optional<UserContact> findByContactTypeAndApprovedAndUser(UserContact.ContactType contactType, boolean approved, User user);

    List<UserContact> findByUserAndApproved(User user, boolean approved);

}
