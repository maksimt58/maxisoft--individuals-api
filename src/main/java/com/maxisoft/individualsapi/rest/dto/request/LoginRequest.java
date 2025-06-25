package com.maxisoft.individualsapi.rest.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
