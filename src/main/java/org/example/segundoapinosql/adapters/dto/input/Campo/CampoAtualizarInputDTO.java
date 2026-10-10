package org.example.segundoapinosql.adapters.dto.input.Campo;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.segundoapinosql.adapters.dto.input.Opcao.OpcaoAtualizarInputDTO;
import org.example.segundoapinosql.domain.enums.TipoCampo;

public record CampoAtualizarInputDTO(
        String id,
        String nome,
        TipoCampo tipoCampo,
        Boolean obrigatorio,
        List<OpcaoAtualizarInputDTO> opcoes
) {
}
