package com.goganesh.bookshop.webapi.client.exception;

public class NoSuchAuthorException extends RuntimeException {

    public static final String NO_AUTHOR_ID = "No such author with id ";

    public NoSuchAuthorException(String message) {
        super(message);
    }
}
