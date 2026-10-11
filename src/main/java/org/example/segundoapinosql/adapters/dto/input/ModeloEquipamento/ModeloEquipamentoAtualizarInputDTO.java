package org.example.segundoapinosql.adapters.dto.input.ModeloEquipamento;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.example.segundoapinosql.adapters.dto.input.Equipamento.EquipamentoAtualizarInputDTO;

public record ModeloEquipamentoAtualizarInputDTO(
        @NotNull(message = "validation.equipamentoId.required")
        Long modeloEquipamentoId,

        List<EquipamentoAtualizarInputDTO> equipamentos
) {
}
