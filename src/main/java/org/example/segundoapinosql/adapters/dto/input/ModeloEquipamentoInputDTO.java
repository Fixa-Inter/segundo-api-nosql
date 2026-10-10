package org.example.segundoapinosql.adapters.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ModeloEquipamentoInputDTO (

        @NotNull(message = "validation.modeloEquipamento.id.required")
        Long modeloEquipamentoId,

        @NotBlank(message = "validation.modeloEquipamento.nome.required")
        String nome,

        @NotEmpty(message = "validation.modeloEquipamento.equipamentos.required")
        List<@Valid EquipamentoInputDTO> equipamentos
) {
}
