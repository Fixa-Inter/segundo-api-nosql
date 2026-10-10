package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.InspecaoChecklist;
import org.example.segundoapinosql.infrastructure.database.mongo.document.InspecaoChecklistDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InspecaoChecklistMapper {

    InspecaoChecklistDocument toDocument(InspecaoChecklist model);
    InspecaoChecklist toModel(InspecaoChecklistDocument entity);

}
