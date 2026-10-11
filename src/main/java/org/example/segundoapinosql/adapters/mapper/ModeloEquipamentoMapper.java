package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.ModeloEquipamento;
import org.example.segundoapinosql.infrastructure.database.sql.entity.ModeloEquipamentoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModeloEquipamentoMapper {
    ModeloEquipamento toModel(ModeloEquipamentoEntity entity);
}
