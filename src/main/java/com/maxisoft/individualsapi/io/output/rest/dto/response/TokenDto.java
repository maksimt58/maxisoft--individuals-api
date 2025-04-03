package com.maxisoft.individualsapi.io.output.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TokenDto(
        String accessToken,
        Integer expiresIn,
        Integer refreshExpiresIn,
        String refreshToken,
        String tokenType,
        @JsonProperty("not-before-policy")
        Integer notBeforePolicy,
        UUID sessionState,
        String scope
) {
}