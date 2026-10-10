package org.example.segundoapinosql.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoAcesso {
    ADMINISTRADOR("Administrador", 1),
    GESTOR("Gestor", 2),
    TECNICO("Técnico", 3),
    SOLICITANTE("Solicitante", 4);

    private final String nome;
    private final int id;

    public static TipoAcesso fromId(int id) {
        for (TipoAcesso tipoAcesso : values()) {
            if (tipoAcesso.id == id) {
                return tipoAcesso;
            }
        }

        throw new IllegalArgumentException("Tipo de acesso inválido: " + id);
    }

}
