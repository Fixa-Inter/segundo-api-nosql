package org.example.segundoapinosql.infrastructure.database.redis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.domain.model.Notificacao;
import org.example.segundoapinosql.domain.repository.NotificacaoRepository;
import org.example.segundoapinosql.infrastructure.database.redis.entity.NotificacaoEntity;
import org.example.segundoapinosql.infrastructure.database.redis.repository.JpaNotificacaoRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificacaoRedisRepository implements NotificacaoRepository {
    private static final int MAX_PER_USER = 200;

    private final JpaNotificacaoRepository repository;

    @Override
    public Notificacao salvar(Notificacao notificacao) {
        NotificacaoEntity entity = new NotificacaoEntity(
                notificacao.getId(), notificacao.getUsuarioId(), notificacao.getTipoNotificacao(),
                notificacao.getReferenciaId(), notificacao.getMensagem(), notificacao.getDataCriacao(),
                notificacao.isVisualizada()
        );

        NotificacaoEntity saved = repository.save(entity);
        limitarHistorico(notificacao.getUsuarioId());
        return toModel(saved);
    }

    private void limitarHistorico(Long usuarioId) {
        List<NotificacaoEntity> notificacoes = repository.findAllByUsuarioId(usuarioId);
        notificacoes.stream()
                .sorted(Comparator.comparing(NotificacaoEntity::getDataCriacao).reversed())
                .skip(MAX_PER_USER)
                .forEach(repository::delete);
    }

    private Notificacao toModel(NotificacaoEntity entity) {
        return new Notificacao(entity.getId(), entity.getUsuarioId(), entity.getTipoNotificacao(),
                entity.getReferenciaId(), entity.getMensagem(), entity.getDataCriacao(), entity.getVisualizada());
    }
}
