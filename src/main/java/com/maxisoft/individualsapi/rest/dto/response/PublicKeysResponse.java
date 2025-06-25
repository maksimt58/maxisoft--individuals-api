package com.maxisoft.individualsapi.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PublicKeysResponse(
        List<Key> key
) {
    public record Key(
            @JsonProperty("kid")
            String id,
            @JsonProperty("n")
            String modulus,
            @JsonProperty("e")
            String exponent
    ) {
    }
}
