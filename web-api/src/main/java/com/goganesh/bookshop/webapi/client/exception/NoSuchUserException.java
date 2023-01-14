package com.goganesh.bookshop.webapi.client.exception;

public class NoSuchUserException extends RuntimeException {

    public static final String NO_USER_ID = "No such user with id ";

    public NoSuchUserException(String message) {
        super(message);
    }
}
