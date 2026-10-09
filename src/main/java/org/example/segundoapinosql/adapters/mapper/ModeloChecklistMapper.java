package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.infrastructure.database.mongo.document.ModeloChecklistDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModeloChecklistMapper {

    ModeloChecklistDocument toDocument(ModeloChecklist model);
    ModeloChecklist toModel(ModeloChecklistDocument entity);

}
