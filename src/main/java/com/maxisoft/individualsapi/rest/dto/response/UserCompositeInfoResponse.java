package com.maxisoft.individualsapi.rest.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record UserCompositeInfoResponse(
        String id,
        String email,
        List<Role> roles,
        String createdAt
) {
    public enum Role {
        USER,
        ADMIN,
        MANAGER
    }
}
