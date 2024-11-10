package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.LoginAttempt;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.repository.LoginAttemptRepository;
import com.goganesh.security.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final int maxAttempt;
    private final int blockTimeMinutes;
    private final LoginAttemptRepository loginAttemptRepository;

    public LoginAttemptServiceImpl(@Value("${com.goganesh.bookshop.login-attempt.max-attempt}") int maxAttempt,
                                   @Value("${com.goganesh.bookshop.login-attempt.block-time-minutes}") int blockTimeMinutes,
                                   LoginAttemptRepository loginAttemptRepository) {
        this.maxAttempt = maxAttempt;
        this.blockTimeMinutes = blockTimeMinutes;
        this.loginAttemptRepository = loginAttemptRepository;
    }

    @Override
    public void loginFailed(User user, LoginAttempt.LoginType loginType) {
        loginAttemptRepository.findByUserAndLoginType(user, loginType)
                .ifPresentOrElse(
                        loginAttempt -> {
                            int increment = loginAttempt.getAttemptCount() + 1;
                            loginAttempt.setAttemptCount(increment);
                            loginAttemptRepository.save(loginAttempt);
                        },
                        () -> loginAttemptRepository.save(
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
        return false;
    }
}
