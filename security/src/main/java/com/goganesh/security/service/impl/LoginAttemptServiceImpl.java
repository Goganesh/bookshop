package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.LoginAttempt;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.service.LoginAttemptReadRepository;
import com.goganesh.bookshop.model.service.LoginAttemptWriteRepository;
import com.goganesh.security.service.LoginAttemptService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final int maxAttempt;
    private final int blockTimeMinutes;
    private final LoginAttemptReadRepository loginAttemptReadRepository;
    private final LoginAttemptWriteRepository loginAttemptWriteRepository;

    @Override
    public void loginFailed(User user, LoginAttempt.LoginType loginType) {
        loginAttemptReadRepository.findByUserAndLoginType(user, loginType)
                .ifPresentOrElse(
                        loginAttempt -> {
                            int increment = loginAttempt.getAttemptCount() + 1;
                            loginAttempt.setAttemptCount(increment);
                            loginAttemptWriteRepository.save(loginAttempt);
                        },
                        () -> loginAttemptWriteRepository.save(
                                LoginAttempt.builder()
                                        .user(user)
                                        .attemptCount(0)
                                        .loginType(loginType)
                                        .attemptTime(LocalDateTime.now())
                                        .build())
                );
    }

    @Override
    public boolean isBlocked(UserContact userContact) {
        //todo вернуть значение
        return false;
    }
}
