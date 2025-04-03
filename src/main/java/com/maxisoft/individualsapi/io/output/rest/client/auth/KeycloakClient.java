package com.maxisoft.individualsapi.io.output.rest.client.auth;

import com.maxisoft.individualsapi.io.input.rest.dto.request.LoginRequest;
import com.maxisoft.individualsapi.io.input.rest.dto.request.RefreshTokenRequest;
import com.maxisoft.individualsapi.io.output.rest.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KeycloakClient {

    private final WebClient keycloakWebClient;
    private final String GRANT_TYPE_PASSWORD = "password";
    private final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    public Mono<TokenDto> getAccessToken(LoginRequest creds) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.put("client_id", List.of("admin-cli"));
        form.put("username", List.of(creds.userName()));
        form.put("password", List.of(creds.password()));
        form.put("grant_type", List.of(GRANT_TYPE_PASSWORD));

        return keycloakWebClient.post()
                .uri("/master/protocol/openid-connect/token")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(TokenDto.class);
    }

    public Mono<TokenDto> getAccessTokenByRefreshToken(RefreshTokenRequest refreshToken) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.put("client_id", List.of("admin-cli"));
        form.put("refresh_token", List.of(refreshToken.refreshToken()));
        form.put("grant_type", List.of(GRANT_TYPE_REFRESH_TOKEN));

        return keycloakWebClient.post()
                .uri("/master/protocol/openid-connect/token")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(TokenDto.class);
    }
}
