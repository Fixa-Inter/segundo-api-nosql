package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.StatusInspecaoEquipamento;
import org.example.segundoapinosql.infrastructure.database.mongo.document.StatusInspecaoEquipamentoDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusInspecaoEquipamentoMapper {

    StatusInspecaoEquipamento toModel(StatusInspecaoEquipamentoDocument document);

    StatusInspecaoEquipamentoDocument toDocument(StatusInspecaoEquipamento model);

}
