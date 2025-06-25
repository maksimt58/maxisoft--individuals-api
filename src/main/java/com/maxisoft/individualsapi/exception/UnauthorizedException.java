package com.maxisoft.individualsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.maxisoft.individualsapi.exception.Errors.UNAUTHORIZED;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(message, UNAUTHORIZED.name());
    }

    public UnauthorizedException(String message, String errorCode) {
        super(message, errorCode);
    }
}
