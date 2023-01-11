package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.LoginAttempt;

public interface LoginAttemptWriteRepository {

    void save(LoginAttempt loginAttempt);
}
