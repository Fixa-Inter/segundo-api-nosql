package org.example.segundoapinosql.adapters.dto.input.ModeloChecklist;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.segundoapinosql.adapters.dto.input.Campo.CampoAtualizarInputDTO;
import org.example.segundoapinosql.adapters.dto.input.ModeloEquipamento.ModeloEquipamentoAtualizarInputDTO;
import org.example.segundoapinosql.adapters.dto.input.Opcao.OpcaoAtualizarInputDTO;

public record ModeloChecklistAtualizarInputDTO(
        @NotNull(message = "validation.equipamentoId.required")
        String id,

        String nome,

        @Positive(message = "validation.modeloChecklist.periodicidade.invalid")
        Integer periodicidade,

        @FutureOrPresent(message = "validation.modeloChecklist.primeiraAbertura.invalid")
        LocalDate primeiraAbertura,

        Boolean obrigatorio,
        List<CampoAtualizarInputDTO> campos
) {
}
