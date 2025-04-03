package com.maxisoft.individualsapi.io.input.rest.dto.request;

public record LoginRequest(
        String userName,
        String password
) {
}
