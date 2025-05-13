package com.maxisoft.individualsapi.exception.auth;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends RuntimeException {
    String error;
    HttpStatus status = HttpStatus.BAD_REQUEST;

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
