package org.example.segundoapinosql.adapters.dto.input.Equipamento;

import jakarta.validation.constraints.NotNull;

public record EquipamentoAtualizarInputDTO(
        @NotNull(message = "validation.equipamentoId.required")
        Long equipamentoId
) {
}
