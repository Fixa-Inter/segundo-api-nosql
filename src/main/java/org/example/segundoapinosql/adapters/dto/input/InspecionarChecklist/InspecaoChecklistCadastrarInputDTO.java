package org.example.segundoapinosql.adapters.dto.input.InspecionarChecklist;

import java.util.Map;

public record InspecaoChecklistCadastrarInputDTO(
        String modeloChecklistId,
        Long equipamentoId,
        Map<String, Object> dados
) {
}
