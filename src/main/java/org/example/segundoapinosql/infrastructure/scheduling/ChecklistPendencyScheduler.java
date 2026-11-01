package org.example.segundoapinosql.infrastructure.scheduling;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.application.usecase.Notification.NotificarChecklistsPendentes;
import org.example.segundoapinosql.domain.enums.StatusExecucaoJob;
import org.example.segundoapinosql.domain.model.ExecucaoJob;
import org.example.segundoapinosql.domain.repository.ExecucaoJobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/** Dispara diariamente os avisos de checklist; Redis evita duplicidade entre instâncias ECS. */
@Component
@RequiredArgsConstructor
public class ChecklistPendencyScheduler {
    private static final Logger log = LoggerFactory.getLogger(ChecklistPendencyScheduler.class);
    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    private final NotificarChecklistsPendentes notificarChecklistsPendentes;
    private final ExecucaoJobRepository execucoes;

    @Scheduled(cron = "${app.jobs.checklist-pendencias.primeiro-cron:0 0 10 * * *}", zone = "America/Sao_Paulo")
    public void enviarPrimeiraNotificacao() {
        executarJob("10h");
    }

    @Scheduled(cron = "${app.jobs.checklist-pendencias.segundo-cron:0 40 16 * * *}", zone = "America/Sao_Paulo")
    public void enviarSegundaNotificacao() {
        executarJob("16h");
    }

    private void executarJob(String horario) {
        LocalDate hoje = LocalDate.now(ZONE_ID);
        ExecucaoJob execucao = new ExecucaoJob(
                "checklists-pendentes:" + hoje + ":" + horario,
                "checklists-pendentes-" + horario,
                hoje,
                LocalDateTime.now(ZONE_ID),
                null,
                StatusExecucaoJob.EM_EXECUCAO,
                null,
                null
        );

        if (!execucoes.iniciarSeAusente(execucao)) {
            log.info("Job de checklists pendentes ignorado: já existe execução para {} em {}.", hoje, horario);
            return;
        }

        try {
            int modelosNotificados = notificarChecklistsPendentes.executar();

            execucao.setFinalizadoEm(LocalDateTime.now(ZONE_ID));
            execucao.setStatus(StatusExecucaoJob.CONCLUIDO);
            execucao.setRegistrosProcessados(modelosNotificados);
            execucoes.atualizar(execucao);
        } catch (RuntimeException exception) {
            execucao.setFinalizadoEm(LocalDateTime.now(ZONE_ID));
            execucao.setStatus(StatusExecucaoJob.FALHOU);
            execucao.setErro(exception.getMessage());

            try {
                execucoes.atualizar(execucao);
            } catch (RuntimeException persistError) {
                exception.addSuppressed(persistError);
            }

            log.error("Falha no job de notificações de checklist pendente das {}.", horario, exception);
        }
    }
}
