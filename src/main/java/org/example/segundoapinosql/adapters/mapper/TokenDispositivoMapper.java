package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.infrastructure.database.redis.entity.TokenDispositivoEntity;
import org.example.segundoapinosql.domain.model.TokenDispositivo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenDispositivoMapper {
    TokenDispositivoEntity toEntity(TokenDispositivo model);
    TokenDispositivo toModel(TokenDispositivoEntity entity);
}
