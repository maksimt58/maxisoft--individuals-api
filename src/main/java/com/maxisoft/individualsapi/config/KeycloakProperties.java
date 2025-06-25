package com.maxisoft.individualsapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "keycloak")
@Component
public class KeycloakProperties {
    private String clientId;
    private String clientSecret;
    private String clientUsername;
    private String clientPassword;
}

