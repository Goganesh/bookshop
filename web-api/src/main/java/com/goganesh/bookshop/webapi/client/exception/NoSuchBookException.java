package com.goganesh.bookshop.webapi.client.exception;

public class NoSuchBookException extends RuntimeException {

    public static final String NO_BOOK_ID = "No such book with id ";

    public NoSuchBookException(String message) {
        super(message);
    }
}
