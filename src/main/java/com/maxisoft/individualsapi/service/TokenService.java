package com.maxisoft.individualsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxisoft.individualsapi.exception.InvalidCredentialsException;
import com.maxisoft.individualsapi.rest.client.auth.KeycloakClient;
import com.maxisoft.individualsapi.rest.dto.request.LoginRequest;
import com.maxisoft.individualsapi.rest.dto.request.RefreshTokenRequest;
import com.maxisoft.individualsapi.rest.dto.request.RegistrationRequest;
import com.maxisoft.individualsapi.rest.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserService userService;
    private final KeycloakClient keycloakClient;
    private final ObjectMapper objectMapper;

    public Mono<TokenResponse> getAccessToken(RegistrationRequest userData) {
        var isEqualsPass = userData.password().equals(userData.confirmPassword());
        if (!isEqualsPass) throw new InvalidCredentialsException("Password confirmation does not match");

        return getInternalToken().flatMap(tokenResponse ->
                userService.createUser(userData, tokenResponse.accessToken())
                        .then(getAccessToken(new LoginRequest(userData.email(), userData.password())))
        );
    }

    public Mono<TokenResponse> getAccessToken(LoginRequest creds) {
        return keycloakClient.getAccessToken(creds);
    }

    public Mono<TokenResponse> refreshToken(RefreshTokenRequest refreshToken) {
        return keycloakClient.getRefreshAccessToken(refreshToken);
    }

    private Mono<TokenResponse> getInternalToken() {
        return keycloakClient.getInternalAccessToken();
    }

    public String getClaimDataFromTokenPayload(String token, String claimName) {
        var encodedPayload = token.split("\\.")[1];
        var decodedPayload = Base64.getDecoder().decode(encodedPayload);

        try {
            return objectMapper.readTree(decodedPayload).get(claimName).asText();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Couldn't extract claim: %s from the token", claimName), e);
        }
    }
}
