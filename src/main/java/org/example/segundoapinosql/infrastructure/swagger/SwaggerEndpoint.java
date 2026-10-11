package org.example.segundoapinosql.infrastructure.swagger;

public record SwaggerEndpoint(
        String method,
        String path,
        String tag,
        String summary,
        String description
) {
}
