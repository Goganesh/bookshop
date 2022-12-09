package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.JpaUserRepository;
import com.goganesh.bookshop.model.service.UserWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaUserWriteRepository implements UserWriteRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public void delete(User user) {
        jpaUserRepository.delete(user);
    }
}