package com.maxisoft.individualsapi.rest.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TokenResponse(
        String accessToken,
        Integer expiresIn,
        String refreshToken,
        Integer refreshExpiresIn,
        String tokenType
) {
}