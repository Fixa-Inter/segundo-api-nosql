package org.example.segundoapinosql.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "firebase")
public record FirebaseProperties(
        String projectId,
        String credentialsPath,
        String credentialsJson
) {
}
