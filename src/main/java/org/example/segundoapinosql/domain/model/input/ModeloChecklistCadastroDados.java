package org.example.segundoapinosql.domain.model.input;

import org.example.segundoapinosql.domain.enums.TipoCampo;

import java.time.LocalDate;
import java.util.List;

/** Dados de entrada internos para cadastrar um checklist, independentes do formato HTTP. */
public record ModeloChecklistCadastroDados(
        String nome,
        Integer periodicidade,
        LocalDate primeiraAbertura,
        Boolean obrigatorio,
        List<Campo> campos,
        List<ModeloEquipamento> modelos
) {
    public record Campo(String nome, TipoCampo tipoCampo, Boolean obrigatorio, List<Opcao> opcoes) { }
    public record Opcao(String nome, Boolean obrigatorio) { }
    public record ModeloEquipamento(Long modeloEquipamentoId, List<Equipamento> equipamentos) { }
    public record Equipamento(Long equipamentoId) { }
}
