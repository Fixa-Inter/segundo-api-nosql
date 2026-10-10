package org.example.segundoapinosql.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeloEquipamento {
    private Long modeloEquipamentoId;
    private String nome;
    private List<Equipamento> equipamentos;
}
