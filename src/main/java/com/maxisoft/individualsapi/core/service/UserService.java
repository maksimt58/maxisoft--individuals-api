package com.maxisoft.individualsapi.core.service;

import com.maxisoft.individualsapi.io.output.rest.client.auth.KeycloakClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakClient keycloakClient;


}
