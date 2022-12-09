package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.LoginAttempt;
import com.goganesh.bookshop.model.repository.JpaLoginAttemptRepository;
import com.goganesh.bookshop.model.service.LoginAttemptWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaLoginAttemptWriteRepository implements LoginAttemptWriteRepository {

    private final JpaLoginAttemptRepository jpaLoginAttemptRepository;

    @Override
    public void save(LoginAttempt loginAttempt) {
        jpaLoginAttemptRepository.save(loginAttempt);
    }
}
