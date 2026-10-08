package org.example.segundoapinosql.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TipoCampo {

    TEXTO("Texto", "String"),
    NUMERO("Número", "Double"),
    BOOLEANO("Sim ou Não", "Boolean"),
    RADIO("Uma Opção", "List<Opcao>"),
    CHECK("Várias Opções", "List<Opcao>"),
    AVALIACAO("Avaliação 1-5", "Integer");

    private String nome;
    private String tipoJava;

    public static TipoCampo fromNome(String nome) {
        for (TipoCampo tipoCampo : values()) {
            if(tipoCampo.getNome().equals(nome)) {
                return tipoCampo;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + nome);
    }
}
