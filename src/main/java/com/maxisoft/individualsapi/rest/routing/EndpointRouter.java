package com.maxisoft.individualsapi.rest.routing;

import com.maxisoft.individualsapi.rest.handler.AuthHandler;
import com.maxisoft.individualsapi.rest.handler.UserHandler;
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
public class EndpointRouter {

    private final AuthHandler authHandler;
    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> authRoutes() {
        return route()
                .POST("/v1/auth/registration", accept(APPLICATION_JSON), authHandler::registration)
                .POST("/v1/auth/login", accept(APPLICATION_JSON), authHandler::login)
                .POST("/v1/auth/refresh-token", accept(APPLICATION_JSON), authHandler::refreshToken)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        return route()
                .GET("/v1/users/me", accept(APPLICATION_JSON), userHandler::getCurrentUser)
                .build();
    }
}
