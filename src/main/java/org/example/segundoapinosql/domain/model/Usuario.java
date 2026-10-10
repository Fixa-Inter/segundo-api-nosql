package org.example.segundoapinosql.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.segundoapinosql.domain.enums.TipoAcesso;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private Long enderecoId;
    private String nomeCompleto;
    private String email;
    private TipoAcesso tipoAcesso;
}
