package org.example.segundoapinosql.adapters.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.segundoapinosql.domain.enums.TipoCampo;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

public record CampoInputDTO(

        @NotBlank(message = "validation.campo.nome.required")
        String nome,

        @NotNull(message = "validation.campo.tipo.required")
        TipoCampo tipoCampo,

        Boolean obrigatorio,
        List<@Valid OpcaoInputDTO> opcoes
) {
}
