package com.maxisoft.individualsapi.io.input.rest.handler;

import com.maxisoft.individualsapi.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService userService;

    public Mono<ServerResponse> getUsers(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return null;
    }
}
