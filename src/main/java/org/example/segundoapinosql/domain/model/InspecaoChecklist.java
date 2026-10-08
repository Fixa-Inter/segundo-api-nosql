package org.example.segundoapinosql.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InspecaoChecklist {
    private Long id;
    private Long modeloEquipamentoId;
    private Long equipamentoId;
    private LocalDate dataAtualizacao;
    private LocalDate prazoInspecao;
    private Boolean concluida;
}
