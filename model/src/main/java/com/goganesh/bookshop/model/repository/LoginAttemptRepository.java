package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.LoginAttempt;
import com.goganesh.bookshop.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Integer> {

    List<LoginAttempt> findByUser(User user);

    Optional<LoginAttempt> findByUserAndLoginType(User user, LoginAttempt.LoginType loginType);
}
