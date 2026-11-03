package org.example.segundoapinosql.application.usecase.Notification;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.PushNotification;
import org.example.segundoapinosql.domain.model.StatusInspecaoEquipamento;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.PushNotificationSender;
import org.example.segundoapinosql.domain.repository.NotificacaoRepository;
import org.example.segundoapinosql.domain.repository.StatusInspecaoEquipamentoRepository;
import org.example.segundoapinosql.domain.repository.TokenDispositivoRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class NotificarChecklistsPendentes {
    private static final Logger log = LoggerFactory.getLogger(NotificarChecklistsPendentes.class);
    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");
    private static final int TIPO_NOTIFICACAO_CHECKLIST = 4;

    private final ModeloChecklistRepository modeloChecklistRepository;
    private final StatusInspecaoEquipamentoRepository statusRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenDispositivoRepository tokenRepository;
    private final PushNotificationSender pushSender;
    private final NotificacaoRepository notificacaoRepository;

    /** Sends one push per active checklist model with at least one equipment pending inspection. */
    public int executar() {
        LocalDate hoje = LocalDate.now(ZONE_ID);
        int modelosNotificados = 0;

        for (ModeloChecklist modelo : modeloChecklistRepository.findAll()) {
            if ( modelo.getPrimeiraAbertura().isAfter(hoje)  || !temEquipamentoPendente(modelo, hoje)) {
                continue;
            }

            List<Long> destinatarios = usuarioRepository
                    .listarGestoresETecnicosAtivosDaUnidade(modelo.getUnidadeId())
                    .stream()
                    .map(usuario -> usuario.getId())
                    .toList();

            if (destinatarios.isEmpty()) continue;

            String mensagem = "O modelo de checklist \"" + modelo.getNome()
                    + "\" possui equipamentos pendentes de inspeção.";

            Set<String> tokens = new HashSet<>();
            LocalDateTime dataCriacao = LocalDateTime.now(ZONE_ID);
            destinatarios.forEach(usuarioId -> {
                try {
                    notificacaoRepository.salvar(new org.example.segundoapinosql.domain.model.Notificacao(
                            UUID.randomUUID().toString(), usuarioId, TIPO_NOTIFICACAO_CHECKLIST,
                            modelo.getId(), mensagem, dataCriacao, false));
                } catch (RuntimeException exception) {
                    log.warn("Não foi possível salvar notificação de checklist {} no Redis para usuário {}.",
                            modelo.getId(), usuarioId, exception);
                }
                tokens.addAll(tokenRepository.listarPorUsuario(usuarioId));
            });

            pushSender.enviar(tokens, new PushNotification(
                    "Checklist pendente",
                    mensagem,
                    TIPO_NOTIFICACAO_CHECKLIST,
                    modelo.getId()
            ));

            modelosNotificados++;
        }

        log.info("Job de checklists pendentes notificou {} modelo(s).", modelosNotificados);
        return modelosNotificados;
    }

    private boolean temEquipamentoPendente(ModeloChecklist modelo, LocalDate hoje) {
        if (modelo.getModelos() == null) return false;

        Set<Long> equipamentoIds = new HashSet<>();
        modelo.getModelos().stream()
                .filter(link -> link.getEquipamentoIds() != null)
                .forEach(link -> equipamentoIds.addAll(link.getEquipamentoIds()));

        if (equipamentoIds.isEmpty()) return false;

        Map<Long, StatusInspecaoEquipamento> statusPorEquipamento = new HashMap<>();
        statusRepository.findAllByModeloChecklistId(modelo.getId())
                .forEach(status -> statusPorEquipamento.put(status.getEquipamentoId(), status));

        return equipamentoIds.stream().anyMatch(equipamentoId -> {
            StatusInspecaoEquipamento status = statusPorEquipamento.get(equipamentoId);
            return !inspecaoRealizada(status, hoje);
        });

    }

    private boolean inspecaoRealizada(StatusInspecaoEquipamento status, LocalDate hoje) {
        return status != null
                && status.getDataUltimaInspecao() != null
                && status.getPrazoInspecao() != null
                && !status.getDataUltimaInspecao().isAfter(status.getPrazoInspecao())
                && !hoje.isAfter(status.getPrazoInspecao());
    }
}
