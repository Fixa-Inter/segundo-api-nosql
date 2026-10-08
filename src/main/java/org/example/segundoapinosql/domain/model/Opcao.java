package org.example.segundoapinosql.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Opcao {
    private Long id;
    private String nome;
    private Boolean selecionado;
    private Boolean obrigatorio;
}
