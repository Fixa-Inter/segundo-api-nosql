package org.example.segundoapinosql.adapters.dto.input.Equipamento;

import jakarta.validation.constraints.NotNull;

public record EquipamentoCadastrarInputDTO(

        @NotNull(message = "validation.equipamento.required")
        Long equipamentoId

) {
}
