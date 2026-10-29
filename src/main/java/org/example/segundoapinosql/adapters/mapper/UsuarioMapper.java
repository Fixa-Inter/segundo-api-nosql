package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.infrastructure.database.sql.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "unidade.id", target = "unidadeId")
    Usuario toModel(UsuarioEntity entity);

}
