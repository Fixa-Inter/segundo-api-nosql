package org.example.segundoapinosql.adapters.dto.input.Opcao;

import jakarta.validation.constraints.NotBlank;

public record OpcaoCadastrarInputDTO(

        @NotBlank(message = "validation.opcao.nome.required")
        String nome,

        Boolean obrigatorio
) {
}
