package org.example.segundoapinosql.adapters.dto.output;

import java.time.LocalDate;

public record EquipamentoOutputDTO(
        Long equipamentoId,
        String codigo,
        LocalDate prazoInspecao,
        Boolean status
) {
}
