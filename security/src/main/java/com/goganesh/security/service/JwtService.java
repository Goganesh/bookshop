package com.goganesh.security.service;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtService {
    String generateToken(String userId);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String extractUsername(String token);
}
