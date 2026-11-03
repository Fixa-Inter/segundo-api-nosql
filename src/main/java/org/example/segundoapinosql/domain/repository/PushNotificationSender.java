package org.example.segundoapinosql.domain.repository;

import org.example.segundoapinosql.domain.model.PushNotification;

import java.util.Set;

/** Porta de saída para entrega de notificações push. */
public interface PushNotificationSender {
    void enviar(Set<String> tokens, PushNotification notification);
}
