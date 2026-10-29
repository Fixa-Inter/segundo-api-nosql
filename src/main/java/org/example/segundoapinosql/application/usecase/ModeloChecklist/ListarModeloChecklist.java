package org.example.segundoapinosql.application.usecase.ModeloChecklist;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.domain.repository.ModeloEquipamentoRepository;
import org.example.segundoapinosql.domain.repository.EquipamentoRepository;
import org.example.segundoapinosql.domain.model.ModeloEquipamentoChecklist;
import org.example.segundoapinosql.domain.model.ModeloEquipamento;
import org.example.segundoapinosql.domain.model.Equipamento;
import org.example.segundoapinosql.infrastructure.exception.EntidadeNaoEncontradaException;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarModeloChecklist {

    private final ModeloChecklistRepository modeloChecklistRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModeloEquipamentoRepository modeloEquipamentoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public List<ModeloChecklist> listar(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("exception.usuario.required"));

        List<ModeloChecklist> modelos = modeloChecklistRepository.findAll(usuario.getUnidadeId());
        if (modelos.isEmpty()) throw new EntidadeNaoEncontradaException("exception.modeloChecklist.notFound");

        modelos.forEach(modelo -> modelo.setModelos(sanitizarModelos(modelo, usuario)));
        return modelos;
    }

    private List<ModeloEquipamentoChecklist> sanitizarModelos(ModeloChecklist checklist, Usuario usuario) {
        if (checklist.getModelos() == null) return List.of();

        return checklist.getModelos().stream()
                .map(link -> modeloEquipamentoRepository.findById(link.getModeloEquipamentoId())
                        .map(modelo -> new ModeloEquipamentoChecklist(
                                modelo.getId(),
                                equipamentosValidos(link, modelo, usuario)))
                        .orElse(null))
                .filter(java.util.Objects::nonNull)
                .toList();
    }

    private List<Long> equipamentosValidos(ModeloEquipamentoChecklist link, ModeloEquipamento modelo, Usuario usuario) {
        var categoria = modelo.getCategoriaEquipamento();
        var proprietarioModelo = categoria == null ? null : categoria.getUsuario();
        if (proprietarioModelo == null
                || !java.util.Objects.equals(proprietarioModelo.getUnidadeId(), usuario.getUnidadeId())
                || link.getEquipamentoIds() == null) return List.of();

        return link.getEquipamentoIds().stream()
                .map(id -> equipamentoRepository.findById(id).orElse(null))
                .filter(java.util.Objects::nonNull)
                .filter(equipamento -> equipamento.getUsuario() != null
                        && java.util.Objects.equals(equipamento.getUsuario().getUnidadeId(), usuario.getUnidadeId()))
                .filter(equipamento -> equipamento.getModeloEquipamento() != null
                        && java.util.Objects.equals(equipamento.getModeloEquipamento().getId(), modelo.getId()))
                .map(Equipamento::getId)
                .toList();
    }

}
