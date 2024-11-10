package com.goganesh.bookshop.common.exception;

//todo change to some common spring exception
public class NoSuchBookActionException extends RuntimeException {

    public NoSuchBookActionException(String message) {
        super(message);
    }
}
