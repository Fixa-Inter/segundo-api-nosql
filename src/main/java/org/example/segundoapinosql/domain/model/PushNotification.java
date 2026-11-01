package org.example.segundoapinosql.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushNotification {
    private String title;
    private String message;
    private int tipoNotificacao;
    private String referenciaId;
    private Set<Long> usuariosDestinatarios;
}
