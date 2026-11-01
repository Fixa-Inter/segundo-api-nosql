package org.example.segundoapinosql.infrastructure.database.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@RedisHash(value = "notificacao", timeToLive = 2_592_000L)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoEntity {
    @Id
    private String id;

    @Indexed
    private Long usuarioId;

    private Integer tipoNotificacao;
    private String referenciaId;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean visualizada;
}
