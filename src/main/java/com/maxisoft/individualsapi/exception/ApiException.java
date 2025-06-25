package com.maxisoft.individualsapi.exception;


import lombok.Getter;

import static com.maxisoft.individualsapi.exception.Errors.UNKNOWN;
public class ApiException extends RuntimeException{

    @Getter
    protected String errorCode;

    public ApiException(String message) {
        super(message);
        this.errorCode = UNKNOWN.name();
    }

    public ApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
