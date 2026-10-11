package org.example.segundoapinosql.adapters.utils;

import org.example.segundoapinosql.infrastructure.security.JwtAuthenticatedUser;
import org.springframework.security.core.Authentication;

public final class ControllerUtils {

    private ControllerUtils() { }

    public static Long usuarioId(Authentication authentication) {
        return ((JwtAuthenticatedUser) authentication.getPrincipal()).id();
    }

    public static boolean isGestor(Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(
                        authority -> "ROLE_GESTOR"
                                .equals(authority.getAuthority())
                );
    }
}

