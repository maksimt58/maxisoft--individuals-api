package com.maxisoft.individualsapi.rest.handler;

import com.maxisoft.individualsapi.rest.dto.request.LoginRequest;
import com.maxisoft.individualsapi.rest.dto.request.RefreshTokenRequest;
import com.maxisoft.individualsapi.rest.dto.request.RegistrationRequest;
import com.maxisoft.individualsapi.rest.dto.response.TokenResponse;
import com.maxisoft.individualsapi.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final TokenService tokenService;

    public Mono<ServerResponse> registration(ServerRequest request) {
        return request.bodyToMono(RegistrationRequest.class)
                .flatMap(tokenService::getAccessToken)
                .flatMap(this::buildTokenResponse);
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(tokenService::getAccessToken)
                .flatMap(this::buildTokenResponse);
    }

    public Mono<ServerResponse> refreshToken(ServerRequest request) {
        return request.bodyToMono(RefreshTokenRequest.class)
                .flatMap(tokenService::refreshToken)
                .flatMap(this::buildTokenResponse);
    }

    private Mono<ServerResponse> buildTokenResponse(TokenResponse tokenResponse) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(tokenResponse);
    }
}
