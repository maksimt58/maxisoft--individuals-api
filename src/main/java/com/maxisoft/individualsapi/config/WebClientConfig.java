package com.maxisoft.individualsapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class WebClientConfig {

    @Bean
    public WebClient keycloakWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder
                .baseUrl("http://localhost:9090")
                .build();
    }
}
