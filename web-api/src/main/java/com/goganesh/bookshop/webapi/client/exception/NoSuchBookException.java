package com.goganesh.bookshop.webapi.client.exception;

public class NoSuchBookException extends RuntimeException {

    public NoSuchBookException(String message) {
        super(message);
    }
}
