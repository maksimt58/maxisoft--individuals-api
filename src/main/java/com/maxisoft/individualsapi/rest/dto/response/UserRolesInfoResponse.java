package com.maxisoft.individualsapi.rest.dto.response;

import java.util.List;

public record UserRolesInfoResponse(
        List<Role> realmMappings
) {
    public record Role(
            String name
    ) {
    }
}
