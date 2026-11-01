package org.example.segundoapinosql.infrastructure.database.redis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.mapper.TokenDispositivoMapper;
import org.example.segundoapinosql.domain.model.TokenDispositivo;
import org.example.segundoapinosql.domain.repository.TokenDispositivoRepository;
import org.example.segundoapinosql.infrastructure.database.redis.repository.JpaTokenDispositivoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TokenDispositivoRedisRepository implements TokenDispositivoRepository {

    private final JpaTokenDispositivoRepository repository;
    private final TokenDispositivoMapper mapper;

    @Override
    public Set<String> listarPorUsuario(Long usuarioId) {
        return repository.findAllByUsuarioId(usuarioId).stream()
                .map(mapper::toModel)
                .map(TokenDispositivo::getToken)
                .collect(Collectors.toSet());
    }

    @Override
    public void remover(String token) {
        repository.deleteById(token);
    }
}
