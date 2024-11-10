package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Integer> {
    Optional<InvalidToken> findByToken(String token);
}
