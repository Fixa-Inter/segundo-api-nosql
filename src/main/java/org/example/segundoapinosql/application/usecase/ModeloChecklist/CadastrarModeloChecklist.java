package org.example.segundoapinosql.application.usecase.ModeloChecklist;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklistInputDTO;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.domain.model.Campo;
import org.example.segundoapinosql.domain.model.Equipamento;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.ModeloEquipamento;
import org.example.segundoapinosql.domain.model.Opcao;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.adapters.mapper.ModeloChecklistMapper;
import org.example.segundoapinosql.infrastructure.exception.EntidadeNaoEncontradaException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class CadastrarModeloChecklist {

    private final ModeloChecklistRepository modeloChecklistRepository;
    private final UsuarioRepository usuarioRepository;

    public ModeloChecklist cadastrar(ModeloChecklistInputDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Usuário não encontrado: " + usuarioId
                ));

        List<Campo> campos = dto.campos().stream()
                .map(campo -> new Campo(
                        UUID.randomUUID().toString(),
                        campo.nome(),
                        campo.tipoCampo(),
                        Boolean.TRUE.equals(campo.obrigatorio()),
                        campo.opcoes() == null
                                ? Collections.emptyList()
                                : campo.opcoes().stream()
                                .map(opcao -> new Opcao(
                                        UUID.randomUUID().toString(),
                                        opcao.nome(),
                                        Boolean.TRUE.equals(opcao.obrigatorio())
                                ))
                                .toList()
                ))
                .toList();

        List<ModeloEquipamento> modelos = dto.modelos() == null
                ? Collections.emptyList()
                : dto.modelos().stream()
                .map(modelo -> new ModeloEquipamento(
                        modelo.modeloEquipamentoId(),
                        modelo.nome(),
                        modelo.equipamentos().stream()
                                .map(equipamento -> new Equipamento(
                                        equipamento.id(),
                                        equipamento.nome()
                                ))
                                .toList()
                ))
                .toList();

        ModeloChecklist modeloChecklist = new ModeloChecklist(
                null,
                usuarioId,
                usuario.getEnderecoId(),
                dto.nome(),
                dto.periodicidade(),
                campos,
                dto.obrigatorio() != null ? dto.obrigatorio() : false,
                dto.primeiraAbertura(),
                modelos
        );

        return modeloChecklistRepository.save(modeloChecklist);
    }

}
