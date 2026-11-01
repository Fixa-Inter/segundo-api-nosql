package org.example.segundoapinosql.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {
    private String id;
    private Long usuarioId;
    private int tipoNotificacao;
    private String referenciaId;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private boolean visualizada;
}
