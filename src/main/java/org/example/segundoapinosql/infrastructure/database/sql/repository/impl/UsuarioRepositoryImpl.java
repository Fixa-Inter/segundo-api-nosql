package org.example.segundoapinosql.infrastructure.database.sql.repository.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.mapper.UsuarioMapper;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.enums.TipoAcesso;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.infrastructure.database.sql.repository.JpaUsuarioRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Usuario> listarGestoresETecnicosAtivosDaUnidade(Long unidadeId) {
        return jpaUsuarioRepository.findAllByUnidade_IdAndTipoAcessoInAndEstaAtivoTrue(
                        unidadeId,
                        java.util.List.of(TipoAcesso.GESTOR, TipoAcesso.TECNICO))
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
