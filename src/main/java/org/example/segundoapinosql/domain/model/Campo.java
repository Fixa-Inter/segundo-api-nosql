package org.example.segundoapinosql.domain.model;

import org.example.segundoapinosql.domain.enums.TipoCampo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Campo {
    private String id;
    private String nome;
    private TipoCampo tipoCampo;
    private Boolean obrigatorio;
    private List<Opcao> opcoes;
}
