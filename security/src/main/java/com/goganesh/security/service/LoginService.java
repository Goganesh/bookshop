package com.goganesh.security.service;

import com.goganesh.security.controller.dto.LoginRequest;

public interface LoginService {

    String login(LoginRequest payload);
}
