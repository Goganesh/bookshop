package com.goganesh.bookshop.webapi.client.service;

import com.goganesh.bookshop.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRestService {
     Page<User> findAll(Pageable pageable);

    Optional<User> findById(Integer id);

    void delete(User user);

    User save(User user);
}
