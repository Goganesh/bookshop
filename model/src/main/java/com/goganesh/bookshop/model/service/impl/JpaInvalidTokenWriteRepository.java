package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.InvalidToken;
import com.goganesh.bookshop.model.repository.JpaInvalidTokenRepository;
import com.goganesh.bookshop.model.service.InvalidTokenWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaInvalidTokenWriteRepository implements InvalidTokenWriteRepository {

    private final JpaInvalidTokenRepository jpaInvalidTokenRepository;

    @Override
    public void save(InvalidToken invalidToken) {
        jpaInvalidTokenRepository.save(invalidToken);
    }
}
