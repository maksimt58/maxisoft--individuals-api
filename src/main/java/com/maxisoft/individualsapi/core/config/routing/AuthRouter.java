package com.maxisoft.individualsapi.core.config.routing;

import com.maxisoft.individualsapi.io.input.rest.handler.TokenHandler;
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
public class AuthRouter {

    private final TokenHandler tokenHandler;

    @Bean
    public RouterFunction<ServerResponse> authRoutes(){
        return route()
                .POST("/v1/token", accept(APPLICATION_JSON), tokenHandler::getAccessToken)
                .POST("/v1/token/refresh", accept(APPLICATION_JSON), tokenHandler::refreshAccessToken)
                .build();
    }
}
