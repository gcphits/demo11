package com.viettel.api.error;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidRequestException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}