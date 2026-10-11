package org.example.segundoapinosql.infrastructure.security;

public record JwtAuthenticatedUser(
        Long id,
        String email
) {
}
