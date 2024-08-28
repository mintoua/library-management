package com.syniiq.library_management.presentation.exception;


public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}
