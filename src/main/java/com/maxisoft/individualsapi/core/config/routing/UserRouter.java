package com.maxisoft.individualsapi.core.config.routing;

import com.maxisoft.individualsapi.io.input.rest.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class UserRouter {

    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        return route()
                .GET("/v1/users", accept(APPLICATION_JSON), userHandler::getUsers)
                .GET("/v1/users/{id}", accept(APPLICATION_JSON), userHandler::getUserById)
                .POST("/v1/users", userHandler::createUser)
                .build();
    }
}