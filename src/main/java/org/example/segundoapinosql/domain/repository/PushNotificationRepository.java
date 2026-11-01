package org.example.segundoapinosql.domain.repository;

import org.example.segundoapinosql.domain.model.PushNotification;

import java.util.Set;

public interface PushNotificationRepository {
    void enviar(Set<String> tokens, PushNotification notification);
}
