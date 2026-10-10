package org.example.segundoapinosql.adapters.dto.input.Campo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.segundoapinosql.adapters.dto.input.Opcao.OpcaoCadastrarInputDTO;
import org.example.segundoapinosql.domain.enums.TipoCampo;

import java.util.List;

public record CampoCadastrarInputDTO(

        @NotBlank(message = "validation.campo.nome.required")
        String nome,

        @NotNull(message = "validation.campo.tipo.required")
        TipoCampo tipoCampo,

        Boolean obrigatorio,
        List<@Valid OpcaoCadastrarInputDTO> opcoes
) {
}
