package com.maxisoft.individualsapi.exception;

public class InvalidCredentialsException extends UnauthorizedException{
    public InvalidCredentialsException(String message) {
        super(message, "INVALID_CREDENTIALS");
    }
}
