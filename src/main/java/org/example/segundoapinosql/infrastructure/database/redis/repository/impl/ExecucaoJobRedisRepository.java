package org.example.segundoapinosql.infrastructure.database.redis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.mapper.ExecucaoJobMapper;
import org.example.segundoapinosql.domain.model.ExecucaoJob;
import org.example.segundoapinosql.domain.repository.ExecucaoJobRepository;
import org.example.segundoapinosql.infrastructure.database.redis.entity.ExecucaoJobEntity;
import org.example.segundoapinosql.infrastructure.database.redis.repository.JpaExecucaoJobRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class ExecucaoJobRedisRepository implements ExecucaoJobRepository {
    private static final Duration LOCK_TTL = Duration.ofSeconds(ExecucaoJobEntity.TTL_SECONDS);

    private final JpaExecucaoJobRepository repository;
    private final ExecucaoJobMapper mapper;
    private final StringRedisTemplate redis;

    @Override
    public boolean iniciarSeAusente(ExecucaoJob execucao) {
        String lockKey = "fixa:job-lock:" + execucao.getId();
        Boolean lockAcquired = redis.opsForValue().setIfAbsent(lockKey, "1", LOCK_TTL);
        if (!Boolean.TRUE.equals(lockAcquired)) return false;

        repository.save(mapper.toEntity(execucao));
        return true;
    }

    @Override
    public void atualizar(ExecucaoJob execucao) {
        repository.save(mapper.toEntity(execucao));
    }
}
