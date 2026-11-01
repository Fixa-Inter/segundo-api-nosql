package org.example.segundoapinosql.domain.repository;

import java.util.Set;

public interface TokenDispositivoRepository {
    Set<String> listarPorUsuario(Long usuarioId);

    void remover(String token);
}
