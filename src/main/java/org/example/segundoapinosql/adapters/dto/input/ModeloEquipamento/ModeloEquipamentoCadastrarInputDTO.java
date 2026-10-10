package org.example.segundoapinosql.adapters.dto.input.ModeloEquipamento;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.segundoapinosql.adapters.dto.input.Equipamento.EquipamentoCadastrarInputDTO;

import java.util.List;

public record ModeloEquipamentoCadastrarInputDTO(

        @NotNull(message = "validation.modeloEquipamento.id.required")
        Long modeloEquipamentoId,

        @NotEmpty(message = "validation.modeloEquipamento.equipamentos.required")
        List<@Valid EquipamentoCadastrarInputDTO> equipamentos
) {
}
