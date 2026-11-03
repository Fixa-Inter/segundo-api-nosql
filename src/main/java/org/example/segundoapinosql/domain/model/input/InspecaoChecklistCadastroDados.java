package org.example.segundoapinosql.domain.model.input;

import java.util.Map;

/** Respostas de inspeção já convertidas do contrato HTTP. */
public record InspecaoChecklistCadastroDados(
        String modeloChecklistId,
        Long equipamentoId,
        Map<String, Object> dados
) { }
