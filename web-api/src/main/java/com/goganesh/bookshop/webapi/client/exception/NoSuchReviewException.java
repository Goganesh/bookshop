package com.goganesh.bookshop.webapi.client.exception;

public class NoSuchReviewException extends RuntimeException {

    public static final String NO_REVIEW_ID = "No such review with id ";

    public NoSuchReviewException(String message) {
        super(message);
    }
}
