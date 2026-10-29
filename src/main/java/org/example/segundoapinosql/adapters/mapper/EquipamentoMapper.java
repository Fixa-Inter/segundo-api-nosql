package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.Equipamento;
import org.example.segundoapinosql.infrastructure.database.sql.entity.EquipamentoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, ModeloEquipamentoMapper.class})
public interface EquipamentoMapper {
    Equipamento toModel(EquipamentoEntity entity);
}
