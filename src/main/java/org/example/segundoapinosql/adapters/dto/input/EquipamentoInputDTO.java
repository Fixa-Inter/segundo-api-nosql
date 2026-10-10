package org.example.segundoapinosql.adapters.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipamentoInputDTO(

        @NotNull(message = "validation.equipamento.required")
        Long id,

        @NotBlank(message = "validation.equipamento.nome.required")
        String nome
) {
}
