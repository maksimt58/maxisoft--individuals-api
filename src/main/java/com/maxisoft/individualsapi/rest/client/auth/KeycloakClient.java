package com.maxisoft.individualsapi.rest.client.auth;

import com.maxisoft.individualsapi.config.KeycloakProperties;
import com.maxisoft.individualsapi.exception.UnauthorizedException;
import com.maxisoft.individualsapi.rest.dto.request.CreateUserRequest;
import com.maxisoft.individualsapi.rest.dto.request.LoginRequest;
import com.maxisoft.individualsapi.rest.dto.request.RefreshTokenRequest;
import com.maxisoft.individualsapi.rest.dto.response.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class KeycloakClient {

    private final WebClient keycloakWebClient;
    private final KeycloakProperties keycloakProperties;

    private final String GRANT_TYPE_PASSWORD = "password";
    private final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String AUTHORIZATION_BEARER = "Bearer ";

    //todo причесать
    public Mono<Void> createUser(CreateUserRequest request, String token) {
        return keycloakWebClient.post()
                .uri("/admin/realms/master/users")
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_BEARER + token)
                .bodyValue(request)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError()) {
                        return clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(body -> Mono.error(new UnauthorizedException(body.errorMessage())));
                    }
                    return Mono.empty();
                });
    }

    public Mono<TokenResponse> getAccessToken(LoginRequest creds) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.put("client_id", List.of(keycloakProperties.getClientId()));
        form.put("username", List.of(creds.email()));
        form.put("password", List.of(creds.password()));
        form.put("grant_type", List.of(GRANT_TYPE_PASSWORD));

        return keycloakWebClient.post()
                .uri("/realms/master/protocol/openid-connect/token")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(TokenResponse.class);
    }

    public Mono<TokenResponse> getRefreshAccessToken(RefreshTokenRequest refreshToken) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.put("client_id", List.of(keycloakProperties.getClientId()));
        form.put("refresh_token", List.of(refreshToken.refreshToken()));
        form.put("grant_type", List.of(GRANT_TYPE_REFRESH_TOKEN));

        return keycloakWebClient.post()
                .uri("/realms/master/protocol/openid-connect/token")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(TokenResponse.class);
    }

    //todo причесать
    public Mono<TokenResponse> getInternalAccessToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.put("client_id", List.of(keycloakProperties.getClientId()));
        form.put("username", List.of(keycloakProperties.getClientUsername()));
        form.put("password", List.of(keycloakProperties.getClientPassword()));
        form.put("grant_type", List.of(GRANT_TYPE_PASSWORD));

        return keycloakWebClient.post()
                .uri("/realms/master/protocol/openid-connect/token")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(TokenResponse.class);
    }

    public Mono<UserMainInfoResponse> getUserById(String id, String token) {
        return keycloakWebClient.get()
                .uri("/admin/realms/master/users/{user-id}",id)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_BEARER + token)
                .retrieve()
                .bodyToMono(UserMainInfoResponse.class);
    }

    public Mono<UserRolesInfoResponse> getUserRoles(String id, String token) {
        return keycloakWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/admin/realms/master/users/{user-id}/role-mappings").build(id))
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_BEARER + token)
                .retrieve()
                .bodyToMono(UserRolesInfoResponse.class);
    }

    public Mono<PublicKeysResponse> getPublicKeys(){
        return keycloakWebClient.get()
                .uri("/realms/master/protocol/openid-connect/certs")
                .retrieve()
                .bodyToMono(PublicKeysResponse.class);
    }
}
