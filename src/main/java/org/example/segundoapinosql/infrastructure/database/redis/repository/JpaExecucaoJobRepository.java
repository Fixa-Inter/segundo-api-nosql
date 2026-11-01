package org.example.segundoapinosql.infrastructure.database.redis.repository;

import org.example.segundoapinosql.infrastructure.database.redis.entity.ExecucaoJobEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaExecucaoJobRepository extends CrudRepository<ExecucaoJobEntity, String> {
}
