package org.example.segundoapinosql.infrastructure.database.sql.repository;

import java.util.Optional;
import org.example.segundoapinosql.infrastructure.database.sql.entity.UsuarioEntity;
import org.example.segundoapinosql.domain.enums.TipoAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    List<UsuarioEntity> findAllByUnidade_IdAndTipoAcessoInAndEstaAtivoTrue(
            Long unidadeId,
            Collection<TipoAcesso> tiposAcesso
    );
}
