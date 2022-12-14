package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.JpaUserRepository;
import com.goganesh.bookshop.model.service.UserReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JpaUserReadRepository implements UserReadRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findById(Integer id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return jpaUserRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return jpaUserRepository.findByPhone(phone);
    }

    @Override
    public Optional<User> findByHash(String hash) {
        return jpaUserRepository.findByHash(hash);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll();
    }
}