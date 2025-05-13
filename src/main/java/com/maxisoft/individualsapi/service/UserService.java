package com.maxisoft.individualsapi.service;

import com.maxisoft.individualsapi.rest.client.auth.KeycloakClient;
import com.maxisoft.individualsapi.rest.dto.request.CreateUserRequest;
import com.maxisoft.individualsapi.rest.dto.request.RegistrationRequest;
import com.maxisoft.individualsapi.rest.dto.response.UserCompositeInfoResponse;
import com.maxisoft.individualsapi.rest.dto.response.UserMainInfoResponse;
import com.maxisoft.individualsapi.rest.dto.response.UserRolesInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakClient keycloakClient;
    private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN);

    public Mono<Void> createUser(RegistrationRequest userData, String token) {
        var request = new CreateUserRequest(
                userData.email().split("@")[0],
                userData.email(),
                false,
                true,
                List.of(new CreateUserRequest.Credential(
                        "password",
                        userData.password(),
                        false
                ))
        );

        return keycloakClient.createUser(request, token);
    }

    public Mono<UserCompositeInfoResponse> getCurrentUserInfo(String id, String token) {
        return Mono.zip(
                getUserMainInfoById(id, token),
                getUserRoles(id, token)
        ).flatMap(tuple ->
                {
                    var userMainInfo = tuple.getT1();
                    var userRoles = tuple.getT2();
                    return Mono.just(buildUserCompositeInfoResponse(userMainInfo, userRoles));
                }
        );
    }

    public Mono<UserMainInfoResponse> getUserMainInfoById(String id, String token) {
        return keycloakClient.getUserById(id, token);
    }

    public Mono<UserRolesInfoResponse> getUserRoles(String id, String token) {
        return keycloakClient.getUserRoles(id, token);
    }


    private UserCompositeInfoResponse buildUserCompositeInfoResponse(
            UserMainInfoResponse userMainInfo,
            UserRolesInfoResponse userRoles
    ) {
        System.out.println(userRoles);
        return UserCompositeInfoResponse.builder()
                .id(userMainInfo.id())
                .email(userMainInfo.email())
                .roles(mapRoles(userRoles))
                .createdAt(convertTimestamp(userMainInfo.createdTimestamp()))
                .build();

    }

    private List<UserCompositeInfoResponse.Role> mapRoles(UserRolesInfoResponse userRoles) {
        var VALID_ROLE_NAMES = Arrays.stream(UserCompositeInfoResponse.Role.values())
                .map(Enum::name)
                .collect(Collectors.toSet());

        return userRoles.realmMappings().stream()
                .filter(role -> VALID_ROLE_NAMES.contains(role.name()))
                .map(role -> UserCompositeInfoResponse.Role.valueOf(role.name()))
                .toList();
    }

    private String convertTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneOffset.UTC).format(LOCAL_DATE_TIME_FORMATTER);
    }
}
