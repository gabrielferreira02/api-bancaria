package com.gabrielferreira02.sbBank.core.exceptions;

public class InvalidInvoicePaymentDayException extends RuntimeException {
    public InvalidInvoicePaymentDayException(String message) {
        super(message);
    }
}
