package org.example.segundoapinosql.domain.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeloChecklist {
    private Long id;
    private Long usuarioId;
    private String nome;
    private Integer periodicidade;
    private List<Campo> campos;
    private Boolean obrigatorio;
    private LocalDate primeiraAbertura;
}
