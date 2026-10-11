package org.example.segundoapinosql.adapters.dto.output;

import java.time.LocalDate;
import java.util.List;

public record ModeloChecklistOutputDTO (
        String id,
        String nome,
        Integer periodicidade,
        List<CampoOutputDTO> campos,
        Boolean obrigatorio,
        LocalDate primeiraAbertura,
        List<ModeloEquipamentoOutputDTO> modelos
) {
}
