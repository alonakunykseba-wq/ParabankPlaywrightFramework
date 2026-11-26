package com.parabank.exceptions;

public class InvalidTestDataException extends FrameworkException {
    public InvalidTestDataException(String message) {
        super(message);
    }

    public InvalidTestDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
