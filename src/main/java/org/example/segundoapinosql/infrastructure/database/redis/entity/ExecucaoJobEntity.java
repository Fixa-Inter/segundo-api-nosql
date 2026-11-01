package org.example.segundoapinosql.infrastructure.database.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.segundoapinosql.domain.enums.StatusExecucaoJob;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RedisHash(value = "execucao_job", timeToLive = ExecucaoJobEntity.TTL_SECONDS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecucaoJobEntity {
    public static final long TTL_SECONDS = 93_600L;

    @Id
    private String id;

    private String nome;
    private LocalDate dataExecucao;
    private LocalDateTime iniciadoEm;
    private LocalDateTime finalizadoEm;
    private StatusExecucaoJob status;
    private Integer registrosProcessados;
    private String erro;
}
