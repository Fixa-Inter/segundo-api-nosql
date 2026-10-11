package org.example.segundoapinosql.adapters.dto.auth;

public record AuthenticatedUser(
        Long id,
        String email
) {
}
