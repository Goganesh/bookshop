package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.UserReadRepository;
import com.goganesh.bookshop.model.service.UserWriteRepository;
import com.goganesh.bookshop.webapi.client.service.UserRestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@AllArgsConstructor
public class UserRestServiceImpl implements UserRestService {

    private final UserReadRepository userReadRepository;
    private final UserWriteRepository userWriteRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userReadRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userReadRepository.findById(id);
    }

    @Override
    public void delete(User user) {
        user.setEnabled(false);
        userWriteRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userWriteRepository.save(user);
    }
}
