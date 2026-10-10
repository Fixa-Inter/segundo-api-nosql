package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.infrastructure.database.sql.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toModel(UsuarioEntity entity);

}
