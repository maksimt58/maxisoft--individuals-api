package com.maxisoft.individualsapi.rest.dto.request;

import java.util.List;

public record CreateUserRequest(
        String username,
        String email,
        Boolean emailVerified,
        Boolean enabled,
        List<Credential> credentials
) {
    public record Credential(
            String type,
            String value,
            Boolean temporary
    ) {
    }
}
