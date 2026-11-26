package com.parabank.exceptions;

public class PageTimeoutException extends FrameworkException {
    public PageTimeoutException(String message) {
        super(message);
    }

    public PageTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
