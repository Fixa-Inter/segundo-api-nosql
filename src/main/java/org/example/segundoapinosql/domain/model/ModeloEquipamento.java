package org.example.segundoapinosql.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeloEquipamento {
    private Long id;
    private CategoriaEquipamento categoriaEquipamento;
    private String nome;
}
