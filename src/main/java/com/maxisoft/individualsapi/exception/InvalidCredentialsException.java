package com.maxisoft.individualsapi.exception;

import static com.maxisoft.individualsapi.exception.Errors.INVALID_CREDENTIALS;

public class InvalidCredentialsException extends UnauthorizedException{
    public InvalidCredentialsException(String message) {
        super(message, INVALID_CREDENTIALS.name());
    }
}
