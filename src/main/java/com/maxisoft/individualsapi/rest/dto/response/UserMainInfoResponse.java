package com.maxisoft.individualsapi.rest.dto.response;

public record UserMainInfoResponse(
        String id,
        String email,
        Long createdTimestamp
) {
}
