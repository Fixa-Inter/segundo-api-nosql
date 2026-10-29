package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.CategoriaEquipamento;
import org.example.segundoapinosql.infrastructure.database.sql.entity.CategoriaEquipamentoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface CategoriaEquipamentoMapper {
    CategoriaEquipamento toModel(CategoriaEquipamentoEntity entity);
}
