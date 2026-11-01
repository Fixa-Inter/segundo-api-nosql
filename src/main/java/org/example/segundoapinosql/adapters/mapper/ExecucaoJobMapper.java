package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.ExecucaoJob;
import org.example.segundoapinosql.infrastructure.database.redis.entity.ExecucaoJobEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExecucaoJobMapper {
    ExecucaoJobEntity toEntity(ExecucaoJob model);

    ExecucaoJob toModel(ExecucaoJobEntity entity);
}
