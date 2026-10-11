package org.example.segundoapinosql.adapters.dto.output;

import java.util.List;

public record CampoOutputDTO (
        String id,
        String nome,
        String tipoCampo,
        Boolean obrigatorio,
        List<OpcaoOutputDTO> opcoes
) {
}
