package com.dtn2603.exception;

public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAccountException(String message) {
        super(message);
    }
}
