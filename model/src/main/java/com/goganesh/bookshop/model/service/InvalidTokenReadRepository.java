package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.InvalidToken;

import java.util.Optional;

public interface InvalidTokenReadRepository {

    Optional<InvalidToken> findByToken(String token);
}
