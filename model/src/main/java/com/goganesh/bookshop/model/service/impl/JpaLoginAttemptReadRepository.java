package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.LoginAttempt;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.JpaLoginAttemptRepository;
import com.goganesh.bookshop.model.service.LoginAttemptReadRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class JpaLoginAttemptReadRepository implements LoginAttemptReadRepository {

    private final JpaLoginAttemptRepository jpaLoginAttemptRepository;

    @Override
    public Optional<LoginAttempt> findByUserAndLoginType(User user, LoginAttempt.LoginType loginType) {
        return jpaLoginAttemptRepository.findByUserAndLoginType(user, loginType);
    }
}
