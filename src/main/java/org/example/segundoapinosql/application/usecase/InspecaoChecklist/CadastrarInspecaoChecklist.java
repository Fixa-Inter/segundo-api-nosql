package org.example.segundoapinosql.application.usecase.InspecaoChecklist;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.dto.input.InspecionarChecklist.InspecaoChecklistCadastrarInputDTO;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.domain.model.*;
import org.example.segundoapinosql.domain.repository.InspecaoChecklistRepository;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.StatusInspecaoEquipamentoRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.infrastructure.exception.RegraProblemaException;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.Objects;

@UseCase
@RequiredArgsConstructor
public class CadastrarInspecaoChecklist {

    private final InspecaoChecklistRepository inspecaoChecklistRepository;
    private final ModeloChecklistRepository modeloChecklistRepository;
    private final StatusInspecaoEquipamentoRepository statusInspecaoEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public InspecaoChecklist cadastrar(
            InspecaoChecklistCadastrarInputDTO dto,
            Long usuarioId
    ) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("validation.usuario.required"));

        ModeloChecklist modeloChecklist = modeloChecklistRepository.findById(dto.modeloChecklistId())
                .orElseThrow(() -> new EntityNotFoundException("exception.modeloChecklist.notFound"));

        if (!Objects.equals(modeloChecklist.getEnderecoId(), usuario.getEnderecoId()))
            throw new RegraProblemaException("exception.endereco.required");

        boolean equipamentoSelecionado =
                modeloChecklist.getModelos() != null
                && modeloChecklist.getModelos()
                        .stream()
                        .anyMatch(modelo ->
                                modelo.getEquipamentoIds() != null
                                && modelo.getEquipamentoIds().contains(dto.equipamentoId())
                        );

        if (!equipamentoSelecionado)
            throw new RegraProblemaException("exception.equipamento.notInChecklist");

        validarDados(dto.dados(), modeloChecklist.getCampos());

        InspecaoChecklist inspecaoChecklist = new InspecaoChecklist(
                null,
                dto.modeloChecklistId(),
                dto.equipamentoId(),
                LocalDate.now(),
                dto.dados()
        );

        StatusInspecaoEquipamento statusInspecaoEquipamento = statusInspecaoEquipamentoRepository
                .findByEquipamentoId(dto.equipamentoId()).orElseGet(StatusInspecaoEquipamento::new);

        if (Boolean.TRUE.equals(statusInspecaoEquipamento.getStatus())) {
            throw new RegraProblemaException("exception.inspecao.alreadyCompleted");
        }

        statusInspecaoEquipamento.setEquipamentoId(dto.equipamentoId());
        statusInspecaoEquipamento.setModeloChecklistId(dto.modeloChecklistId());
        statusInspecaoEquipamento.setDataUltimaInspecao(LocalDate.now());
        statusInspecaoEquipamento.setPrazoInspecao(LocalDate.now().plusDays(modeloChecklist.getPeriodicidade()));
        statusInspecaoEquipamento.setStatus(true);

        inspecaoChecklistRepository.save(inspecaoChecklist);
        statusInspecaoEquipamentoRepository.save(statusInspecaoEquipamento);

        return inspecaoChecklist;
    }

    private void validarDados(Map<String, Object> dados, List<Campo> campos) {
        if (dados == null) throw new RegraProblemaException("exception.inspecao.data.required");

        for (Map.Entry<String, Object> resposta : dados.entrySet()) {

            Campo campo = campos.stream()
                    .filter(item -> Objects.equals(item.getId(), resposta.getKey()))
                    .findFirst()
                    .orElseThrow(() -> new RegraProblemaException("exception.inspecao.field.invalid"));

            if (!tipoCompativel(campo, resposta.getValue()))
                throw new RegraProblemaException("exception.inspecao.field.type.invalid");
        }

        campos.stream()
                .filter(campo -> Boolean.TRUE.equals(campo.getObrigatorio()))
                .filter(campo -> !dados.containsKey(campo.getId()) || dados.get(campo.getId()) == null)
                .findFirst()
                .ifPresent(campo -> {
                    throw new RegraProblemaException("exception.inspecao.field.required");
                });
    }

    private boolean tipoCompativel(Campo campo, Object valor) {
        if (valor == null) return !Boolean.TRUE.equals(campo.getObrigatorio());

        return switch (campo.getTipoCampo()) {
            case TEXTO -> valor instanceof String;
            case NUMERO -> valor instanceof Number;
            case BOOLEANO -> valor instanceof Boolean;
            case AVALIACAO -> valor instanceof Number numero
                    && numero.intValue() >= 1 && numero.intValue() <= 5;
            case RADIO -> validarOpcoes(campo, valor, true);
            case CHECK -> validarOpcoes(campo, valor, false);
        };
    }

    private boolean validarOpcoes(Campo campo, Object valor, boolean radio) {
        if (!(valor instanceof Map<?, ?> respostas) || campo.getOpcoes() == null) {
            return false;
        }

        boolean idsValidos = respostas
                .entrySet()
                .stream()
                .allMatch(resposta ->
                        resposta.getKey() instanceof String
                        && resposta.getValue() instanceof Boolean
                        && campo
                                .getOpcoes()
                                .stream()
                                .anyMatch(opcao -> opcao.getId().equals(resposta.getKey()))
        );

        if (!idsValidos) return false;

        long selecionadas = respostas
                .values()
                .stream()
                .filter(Boolean.TRUE::equals)
                .count();

        if (radio && selecionadas > 1) return false;

        return !radio
                || selecionadas == 1
                || (!Boolean.TRUE.equals(campo.getObrigatorio()) && selecionadas == 0);
    }

}
