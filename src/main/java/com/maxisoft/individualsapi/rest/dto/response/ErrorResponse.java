package com.maxisoft.individualsapi.rest.dto.response;

public record ErrorResponse(
        String error,
        String errorMessage
) {
}
