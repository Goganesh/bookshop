package com.goganesh.security.exception;

public class NoJwtTokenException extends RuntimeException {

    public NoJwtTokenException(String message) {
        super(message);
    }
}
