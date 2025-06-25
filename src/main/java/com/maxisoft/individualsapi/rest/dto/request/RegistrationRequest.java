package com.maxisoft.individualsapi.rest.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RegistrationRequest(
        String email,
        String password,
        String confirmPassword
) {
}
