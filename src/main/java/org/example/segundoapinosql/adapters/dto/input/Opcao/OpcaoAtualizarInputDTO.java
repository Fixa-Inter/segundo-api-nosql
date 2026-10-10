package org.example.segundoapinosql.adapters.dto.input.Opcao;

import jakarta.validation.constraints.NotNull;

public record OpcaoAtualizarInputDTO(
        String id,
        String nome,
        Boolean obrigatorio
) {
}
