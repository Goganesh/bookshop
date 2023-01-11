package com.goganesh.security.service;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.security.controller.dto.RegistrationFormRequest;

import java.util.Optional;

public interface UserRegisterService {

    String registerNewUser(RegistrationFormRequest registrationFormDto);

    User getCurrentUser();

    User registerTempUser(String email);

    void blockTempUser(User tempUser, User realUser);

    void deleteBlockedTempUser(User tempUser);

    Optional<User> getUserByHash(String hash);
}
