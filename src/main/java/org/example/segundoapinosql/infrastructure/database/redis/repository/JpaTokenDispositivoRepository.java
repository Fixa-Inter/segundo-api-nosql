package org.example.segundoapinosql.infrastructure.database.redis.repository;

import org.example.segundoapinosql.infrastructure.database.redis.entity.TokenDispositivoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTokenDispositivoRepository extends CrudRepository<TokenDispositivoEntity, String> {
    List<TokenDispositivoEntity> findAllByUsuarioId(Long usuarioId);
}
