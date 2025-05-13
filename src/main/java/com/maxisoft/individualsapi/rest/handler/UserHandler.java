package com.maxisoft.individualsapi.rest.handler;

import com.maxisoft.individualsapi.service.TokenService;
import com.maxisoft.individualsapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    private final TokenService tokenService;
    private final String AUTH_HEADER = "Authorization";

    public Mono<ServerResponse> getCurrentUser(ServerRequest request) {
        var authHeaderData = request.headers().header(AUTH_HEADER);
        if (authHeaderData.isEmpty()) throw new RuntimeException("Invalid or expired access token"); //todo завернуть в кастом и обработчик
        var token = authHeaderData.getFirst().split(" ")[1];

        var userId = tokenService.getClaimDataFromPayload(token, "sub");

        return userService.getCurrentUserInfo(userId, token)
                .flatMap(response ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(response)
                );
    }
}
