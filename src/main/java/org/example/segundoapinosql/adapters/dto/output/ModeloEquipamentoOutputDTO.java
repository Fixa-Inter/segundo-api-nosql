package org.example.segundoapinosql.adapters.dto.output;

import java.util.List;

public record ModeloEquipamentoOutputDTO (
        Long modeloEquipamentoId,
        String nome,
        List<EquipamentoOutputDTO> equipamentos
) {
}
