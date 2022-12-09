package com.goganesh.bookshop.webapi.client.exception;

public class NoSuchBookReviewException extends RuntimeException{

    public NoSuchBookReviewException(String message) {
        super(message);
    }
}
