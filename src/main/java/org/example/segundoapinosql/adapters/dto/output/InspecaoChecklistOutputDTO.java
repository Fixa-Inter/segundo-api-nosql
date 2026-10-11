package org.example.segundoapinosql.adapters.dto.output;

import java.time.LocalDate;
import java.util.Map;

public record InspecaoChecklistOutputDTO(
        String id,
        String modeloChecklistId,
        Long equipamentoId,
        LocalDate dataInspecao,
        Map<String, Object> dados
) {
}
