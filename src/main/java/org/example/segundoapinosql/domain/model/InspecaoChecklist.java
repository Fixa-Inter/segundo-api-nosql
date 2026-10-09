package org.example.segundoapinosql.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InspecaoChecklist {
    private Long id;
    private Long modeloEquipamentoChecklistId;
    private Long equipamentoId;
    private LocalDate dataAtualizacao;
    private LocalDate prazoInspecao;
    private Boolean status;
    private List<RegistroInspecaoChecklist> registros;
}
