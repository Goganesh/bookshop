package com.goganesh.bookshop.common.exception;

public class NoSuchBookActionException extends RuntimeException{

    public NoSuchBookActionException(String message) {
        super(message);
    }
}
