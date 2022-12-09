package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.LoginAttempt;
import com.goganesh.bookshop.model.domain.User;

import java.util.Optional;

public interface LoginAttemptWriteRepository {

    void save(LoginAttempt loginAttempt);
}
