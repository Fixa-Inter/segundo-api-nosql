package org.example.segundoapinosql.infrastructure.database.redis.repository;

import org.example.segundoapinosql.infrastructure.database.redis.entity.NotificacaoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaNotificacaoRepository extends CrudRepository<NotificacaoEntity, String> {
    List<NotificacaoEntity> findAllByUsuarioId(Long usuarioId);
}
