package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.InvalidToken;
import com.goganesh.bookshop.model.repository.JpaInvalidTokenRepository;
import com.goganesh.bookshop.model.service.InvalidTokenReadRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class JpaInvalidTokenReadRepository implements InvalidTokenReadRepository {

    private final JpaInvalidTokenRepository jpaInvalidTokenRepository;

    @Override
    public Optional<InvalidToken> findByToken(String token) {
        return jpaInvalidTokenRepository.findByToken(token);
    }
}
