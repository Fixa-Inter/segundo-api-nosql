package org.example.segundoapinosql.application.usecase.InspecaoChecklist;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.domain.model.*;
import org.example.segundoapinosql.domain.repository.EquipamentoRepository;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.infrastructure.exception.EntidadeNaoEncontradaException;
import org.example.segundoapinosql.infrastructure.exception.RegraProblemaException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class ListarEquipamentos {

    private final EquipamentoRepository equipamentoRepository;
    private final ModeloChecklistRepository modeloChecklistRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Equipamento> listar(String modeloChecklistId, Long modeloEquipamentoId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        ModeloChecklist modelo = modeloChecklistRepository.findById(modeloChecklistId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloChecklist.notFound"));

        if (modelo.getUnidadeId() == null || !usuario.getUnidadeId().equals(modelo.getUnidadeId()))
            throw new RegraProblemaException("exception.unidade.required");

        List<ModeloEquipamentoChecklist> modelosEquipamento = modelo.getModelos() == null
                ? List.of()
                : modelo.getModelos();

        Optional<ModeloEquipamentoChecklist> modeloEquipamentoChecklist = modelosEquipamento
                .stream()
                .filter(modeloEquipamento -> Objects.equals(modeloEquipamento.getModeloEquipamentoId(), modeloEquipamentoId))
                .findFirst();

        if (modeloEquipamentoChecklist.isEmpty()) {
            throw new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound");
        }

        List<Long> equipamentoIds = modeloEquipamentoChecklist.get().getEquipamentoIds() == null
                ? List.of()
                : modeloEquipamentoChecklist.get().getEquipamentoIds();

        return equipamentoIds.stream()
                .map(equipamentoId -> equipamentoRepository.findById(equipamentoId)
                        .orElseThrow(() -> new EntidadeNaoEncontradaException(
                                "exception.equipamento.notFound")))
                .toList();
    }
}
