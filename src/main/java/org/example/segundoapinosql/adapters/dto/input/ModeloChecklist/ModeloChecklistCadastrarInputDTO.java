package org.example.segundoapinosql.adapters.dto.input.ModeloChecklist;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.example.segundoapinosql.adapters.dto.input.Campo.CampoInputDTO;
import org.example.segundoapinosql.adapters.dto.input.ModeloEquipamento.ModeloEquipamentoInputDTO;

import java.time.LocalDate;
import java.util.List;

public record ModeloChecklistCadastrarInputDTO(
        @NotBlank(message = "validation.modeloChecklist.nome.required")
        String nome,

        @NotNull(message = "validation.modeloChecklist.periodicidade.required")
        @Positive(message = "validation.modeloChecklist.periodicidade.invalid")
        Integer periodicidade,

        @NotNull(message = "validation.modeloChecklist.primeiraAbertura.required")
        @FutureOrPresent(message = "validation.modeloChecklist.primeiraAbertura.invalid") LocalDate primeiraAbertura,

        Boolean obrigatorio,

        @NotEmpty(message = "validation.modeloChecklist.campos.required") List<@Valid CampoInputDTO> campos,

        List<@Valid ModeloEquipamentoInputDTO> modelos
) {
}
