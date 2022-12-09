package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.LoginAttempt;
import com.goganesh.bookshop.model.domain.User;

import java.util.Optional;

public interface LoginAttemptReadRepository {

    Optional<LoginAttempt> findByUserAndLoginType(User user, LoginAttempt.LoginType loginType);
}
