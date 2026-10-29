package org.example.segundoapinosql.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipamento {
    private Long id;
    private Usuario usuario;
    private ModeloEquipamento modeloEquipamento;
    private String codigo;
}
