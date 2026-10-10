package org.example.segundoapinosql.adapters.dto.input;

import jakarta.validation.constraints.NotBlank;

public record OpcaoInputDTO(

        @NotBlank(message = "validation.opcao.nome.required")
        String nome,

        Boolean obrigatorio
) {
}
