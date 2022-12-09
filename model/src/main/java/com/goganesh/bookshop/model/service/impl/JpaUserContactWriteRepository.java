package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.repository.JpaUserContactRepository;
import com.goganesh.bookshop.model.service.UserContactWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaUserContactWriteRepository implements UserContactWriteRepository {

    private final JpaUserContactRepository jpaUserContactRepository;

    @Override
    public UserContact save(UserContact userContact) {
        return jpaUserContactRepository.save(userContact);
    }

}
