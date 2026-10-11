package org.example.segundoapinosql.domain.model;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StatusInspecaoEquipamento {
    private String id;
    private Long equipamentoId;
    private String modeloChecklistId;
    private LocalDate dataUltimaInspecao;
    private LocalDate prazoInspecao;
    private Boolean status;
}
