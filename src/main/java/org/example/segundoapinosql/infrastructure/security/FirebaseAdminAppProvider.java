package org.example.segundoapinosql.infrastructure.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.infrastructure.config.FirebaseProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class FirebaseAdminAppProvider {
    private final FirebaseProperties properties;

    public synchronized FirebaseApp getApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) return FirebaseApp.getInstance();
        FirebaseOptions.Builder options = FirebaseOptions.builder().setProjectId(properties.projectId());

        if (properties.credentialsJson() != null && !properties.credentialsJson().isBlank()) {
            options.setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(
                    properties.credentialsJson().getBytes(StandardCharsets.UTF_8))));
            return FirebaseApp.initializeApp(options.build());
        }

        if (properties.credentialsPath() == null || properties.credentialsPath().isBlank()) {
            throw new IllegalStateException("Configure firebase.credentials-path ou firebase.credentials-json");
        }

        try (FileInputStream credentials = new FileInputStream(properties.credentialsPath())) {
            options.setCredentials(GoogleCredentials.fromStream(credentials));
            return FirebaseApp.initializeApp(options.build());
        }

    }
}
