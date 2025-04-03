package com.maxisoft.individualsapi.io.input.rest.handler;

import com.maxisoft.individualsapi.core.service.TokenService;
import com.maxisoft.individualsapi.io.input.rest.dto.request.LoginRequest;
import com.maxisoft.individualsapi.io.input.rest.dto.request.RefreshTokenRequest;
import com.maxisoft.individualsapi.io.output.rest.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class TokenHandler {

    private final TokenService tokenService;

    public Mono<ServerResponse> getAccessToken(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(tokenService::getToken)
                .flatMap(this::buildTokenResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> refreshAccessToken(ServerRequest request) {
        return request.bodyToMono(RefreshTokenRequest.class)
                .flatMap(tokenService::refreshToken)
                .flatMap(this::buildTokenResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private Mono<ServerResponse> buildTokenResponse(TokenDto tokenDto) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(tokenDto);
    }
}
