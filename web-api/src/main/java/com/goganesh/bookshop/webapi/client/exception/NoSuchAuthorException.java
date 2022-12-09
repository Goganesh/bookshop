package com.goganesh.bookshop.webapi.client.exception;

public class NoSuchAuthorException extends RuntimeException{

    public NoSuchAuthorException(String message) {
        super(message);
    }
}
