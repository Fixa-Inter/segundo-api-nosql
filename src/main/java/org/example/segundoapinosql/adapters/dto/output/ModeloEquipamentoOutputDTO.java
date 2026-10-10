package org.example.segundoapinosql.adapters.dto.output;

import java.util.List;

public record ModeloEquipamentoOutputDTO (
        Long modeloEquipamentoId,
        List<Long> equipamentoIds
) {
}
