package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.UserRepository;
import com.goganesh.bookshop.webapi.client.service.UserRestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {

    private final UserRepository userRepository;

    public UserRestServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(User user) {
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
