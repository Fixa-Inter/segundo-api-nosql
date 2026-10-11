package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.adapters.dto.output.InspecaoChecklistOutputDTO;
import org.example.segundoapinosql.domain.model.InspecaoChecklist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InspecaoChecklistOutputMapper {
    InspecaoChecklistOutputDTO toOutputDTO(InspecaoChecklist model);
}
