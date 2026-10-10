package org.example.segundoapinosql.infrastructure.database.sql.repository.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.mapper.UsuarioMapper;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.infrastructure.database.sql.repository.JpaUsuarioRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final JpaUsuarioRepository jpaUsuarioRepository;
    private final UsuarioMapper mapper;

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaUsuarioRepository.findById(id)
                .map(mapper::toModel);
    }
}
