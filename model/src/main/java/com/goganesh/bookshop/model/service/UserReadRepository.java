package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserReadRepository {

    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);

    Optional<User> findByHash(String hash);

    List<User> findAll();
}