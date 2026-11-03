package org.example.segundoapinosql.adapters.utils;

import org.example.segundoapinosql.infrastructure.security.JwtAuthenticatedUser;
import org.example.segundoapinosql.domain.enums.TipoAcesso;
import org.springframework.security.core.Authentication;

public final class ControllerUtils {

    private ControllerUtils() { }

    public static Long usuarioId(Authentication authentication) {
        return ((JwtAuthenticatedUser) authentication.getPrincipal()).id();
    }

    public static TipoAcesso tipoAcesso(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority().replaceFirst("^ROLE_", ""))
                .map(TipoAcesso::valueOf)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de acesso não encontrado no token"));
    }
}

