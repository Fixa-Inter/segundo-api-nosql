package org.example.segundoapinosql.infrastructure.external.notification;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.domain.model.PushNotification;
import org.example.segundoapinosql.domain.model.Notificacao;
import org.example.segundoapinosql.domain.repository.NotificacaoRepository;
import org.example.segundoapinosql.domain.repository.PushNotificationRepository;
import org.example.segundoapinosql.domain.repository.TokenDispositivoRepository;
import org.example.segundoapinosql.infrastructure.security.FirebaseAdminAppProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FirebasePushNotificationRepository implements PushNotificationRepository {
    private static final Logger log = LoggerFactory.getLogger(FirebasePushNotificationRepository.class);
    private static final int MAX_TOKENS_PER_BATCH = 500;
    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    private final FirebaseAdminAppProvider firebaseAppProvider;
    private final TokenDispositivoRepository tokenRepository;
    private final NotificacaoRepository notificacaoRepository;

    @Override
    public void enviar(Set<String> registeredTokens, PushNotification notification) {
        salvarNoRedis(notification);
        if (registeredTokens == null || registeredTokens.isEmpty()) {
            log.info("Push de checklist {} ignorado: nenhum token de dispositivo registrado.", notification.getReferenciaId());
            return;
        }

        List<String> tokens = new ArrayList<>(registeredTokens);
        boolean houveFalhaRecuperavel = false;
        for (int start = 0; start < tokens.size(); start += MAX_TOKENS_PER_BATCH) {
            List<String> batch = tokens.subList(start, Math.min(start + MAX_TOKENS_PER_BATCH, tokens.size()));
            try {
                MulticastMessage message = MulticastMessage.builder()
                        .addAllTokens(batch)
                        .setNotification(Notification.builder()
                                .setTitle(notification.getTitle())
                                .setBody(notification.getMessage())
                                .build())
                        .putAllData(Map.of(
                                "tipoNotificacao", String.valueOf(notification.getTipoNotificacao()),
                                "referenciaId", notification.getReferenciaId(),
                                "mensagem", notification.getMessage()
                        ))
                        .build();

                BatchResponse response = FirebaseMessaging.getInstance(firebaseAppProvider.getApp())
                        .sendEachForMulticast(message);

                log.info("Push checklist {} enviado: {} sucesso(s), {} falha(s).",
                        notification.getReferenciaId(), response.getSuccessCount(), response.getFailureCount());

                for (int i = 0; i < response.getResponses().size(); i++) {
                    SendResponse send = response.getResponses().get(i);
                    if (send.isSuccessful() || send.getException() == null) continue;

                    MessagingErrorCode code = send.getException().getMessagingErrorCode();
                    if (code == MessagingErrorCode.UNREGISTERED || code == MessagingErrorCode.INVALID_ARGUMENT) {
                        tokenRepository.remover(batch.get(i));
                    } else {
                        houveFalhaRecuperavel = true;
                        log.warn("Falha FCM para checklist {} ({}): {}", notification.getReferenciaId(), code,
                                send.getException().getMessage());
                    }
                }
            } catch (Exception exception) {
                throw new IllegalStateException(
                        "Falha ao enviar push FCM do checklist " + notification.getReferenciaId(), exception);
            }
        }

        if (houveFalhaRecuperavel) {
            throw new IllegalStateException("O FCM não conseguiu entregar todos os pushes do checklist "
                    + notification.getReferenciaId());
        }
    }

    private void salvarNoRedis(PushNotification notification) {
        if (notification.getUsuariosDestinatarios() == null) return;

        LocalDateTime dataCriacao = LocalDateTime.now(ZONE_ID);
        for (Long usuarioId : notification.getUsuariosDestinatarios()) {
            if (usuarioId == null) continue;
            try {
                notificacaoRepository.salvar(new Notificacao(
                        UUID.randomUUID().toString(),
                        usuarioId,
                        notification.getTipoNotificacao(),
                        notification.getReferenciaId(),
                        notification.getMessage(),
                        dataCriacao,
                        false
                ));
            } catch (RuntimeException exception) {
                log.warn("Não foi possível persistir notificação de checklist {} para usuário {}.",
                        notification.getReferenciaId(), usuarioId, exception);
            }
        }
    }
}
