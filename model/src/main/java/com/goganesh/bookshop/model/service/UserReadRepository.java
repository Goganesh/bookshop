package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserReadRepository {

    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);

    Optional<User> findByHash(String hash);

    List<User> findAll();
    Page<User> findAll(Pageable pageable);
}