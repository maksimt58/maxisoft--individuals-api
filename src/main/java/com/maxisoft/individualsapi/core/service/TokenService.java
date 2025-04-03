package com.maxisoft.individualsapi.core.service;

import com.maxisoft.individualsapi.io.input.rest.dto.request.LoginRequest;
import com.maxisoft.individualsapi.io.input.rest.dto.request.RefreshTokenRequest;
import com.maxisoft.individualsapi.io.output.rest.client.auth.KeycloakClient;
import com.maxisoft.individualsapi.io.output.rest.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final KeycloakClient keycloakClient;

    public Mono<TokenDto> getToken(LoginRequest creds) {
        return keycloakClient.getAccessToken(creds);
    }

    public Mono<TokenDto> refreshToken(RefreshTokenRequest refreshToken) {
        return keycloakClient.getAccessTokenByRefreshToken(refreshToken);
    }
}
