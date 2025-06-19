package com.gabrielferreira02.sbBank.core.exceptions;

public class InvalidCpfException extends RuntimeException {
    public InvalidCpfException() {
    }

    public InvalidCpfException(String message) {
        super(message);
    }
}
