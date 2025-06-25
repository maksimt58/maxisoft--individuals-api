package com.maxisoft.individualsapi.exception;

import static com.maxisoft.individualsapi.exception.Errors.INVALID_TOKEN;

public class InvalidTokenException extends UnauthorizedException{
    public InvalidTokenException(String message) {
        super(message, INVALID_TOKEN.name());
    }
}
