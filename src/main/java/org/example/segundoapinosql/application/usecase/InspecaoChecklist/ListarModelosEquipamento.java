package org.example.segundoapinosql.application.usecase.InspecaoChecklist;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.application.security.PermissaoChecklist;
import org.example.segundoapinosql.domain.enums.TipoAcesso;
import org.example.segundoapinosql.domain.model.Equipamento;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.ModeloEquipamentoChecklist;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.repository.EquipamentoRepository;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.domain.repository.ModeloEquipamentoRepository;
import org.example.segundoapinosql.domain.repository.EquipamentoRepository;
import org.example.segundoapinosql.application.exception.EntidadeNaoEncontradaException;
import org.example.segundoapinosql.application.exception.RegraProblemaException;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarModelosEquipamento {

    private final ModeloChecklistRepository modeloChecklistRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModeloEquipamentoRepository modeloEquipamentoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public List<ModeloEquipamentoChecklist> listar(String modeloChecklistId, Long usuarioId, TipoAcesso tipoAcesso) {
        PermissaoChecklist.exigirInspecao(tipoAcesso);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        ModeloChecklist modelo = modeloChecklistRepository.findById(modeloChecklistId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloChecklist.notFound"));

        if (modelo.getUnidadeId() == null || !usuario.getUnidadeId().equals(modelo.getUnidadeId()))
            throw new RegraProblemaException("exception.unidade.required");

        List<ModeloEquipamentoChecklist> modelosEquipamento = modelo.getModelos();

        if (modelosEquipamento == null || modelosEquipamento.isEmpty())
            throw new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound");

        return modelosEquipamento.stream()
                .map(link -> modeloEquipamentoRepository.findById(link.getModeloEquipamentoId())
                        .map(modeloEquipamento -> {
                            var categoria = modeloEquipamento.getCategoriaEquipamento();
                            var proprietario = categoria == null ? null : categoria.getUsuario();
                            if (proprietario == null
                                    || !java.util.Objects.equals(proprietario.getUnidadeId(), usuario.getUnidadeId())) {
                                return null;
                            }
                            List<Long> equipamentosValidos = link.getEquipamentoIds() == null ? List.of()
                                    : link.getEquipamentoIds().stream()
                                    .map(equipamentoRepository::findById)
                                    .flatMap(java.util.Optional::stream)
                                    .filter(equipamento -> equipamento.getUsuario() != null
                                            && java.util.Objects.equals(equipamento.getUsuario().getUnidadeId(), usuario.getUnidadeId()))
                                    .filter(equipamento -> equipamento.getModeloEquipamento() != null
                                            && java.util.Objects.equals(equipamento.getModeloEquipamento().getId(), modeloEquipamento.getId()))
                                    .map(org.example.segundoapinosql.domain.model.Equipamento::getId)
                                    .toList();
                            return new ModeloEquipamentoChecklist(modeloEquipamento.getId(), equipamentosValidos);
                        }).orElse(null))
                .filter(java.util.Objects::nonNull)
                .toList();
    }
}
