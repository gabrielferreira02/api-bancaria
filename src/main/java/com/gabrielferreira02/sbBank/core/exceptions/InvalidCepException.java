package com.gabrielferreira02.sbBank.core.exceptions;

public class InvalidCepException extends RuntimeException {
    public InvalidCepException() {
    }

    public InvalidCepException(String message) {
        super(message);
    }
}
