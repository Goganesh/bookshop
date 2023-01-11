package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.User;

public interface UserWriteRepository {

    User save(User user);

    void delete(User user);
}