package org.example.segundoapinosql.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InspecaoChecklist {
    private String id;
    private String modeloChecklistId;
    private Long equipamentoId;
    private LocalDate dataInspecao;
    private Map<String, Object> dados;
}
