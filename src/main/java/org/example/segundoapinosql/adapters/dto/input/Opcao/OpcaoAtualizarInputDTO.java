package org.example.segundoapinosql.adapters.dto.input.Opcao;

import jakarta.validation.constraints.NotNull;

public record OpcaoAtualizarInputDTO(
        @NotNull(message = "validation.id.required")
        String id,

        String nome,
        Boolean obrigatorio
) {
}
