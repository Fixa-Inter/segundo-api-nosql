package org.example.segundoapinosql.application.usecase.ModeloChecklist;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistCadastrarInputDTO;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.domain.model.Campo;
import org.example.segundoapinosql.domain.model.Equipamento;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.ModeloEquipamento;
import org.example.segundoapinosql.domain.model.ModeloEquipamentoChecklist;
import org.example.segundoapinosql.domain.model.Opcao;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.domain.repository.ModeloEquipamentoRepository;
import org.example.segundoapinosql.domain.repository.EquipamentoRepository;
import org.example.segundoapinosql.infrastructure.exception.EntidadeNaoEncontradaException;
import org.example.segundoapinosql.infrastructure.exception.RegraProblemaException;
import org.example.segundoapinosql.adapters.dto.input.ModeloEquipamento.ModeloEquipamentoCadastrarInputDTO;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class CadastrarModeloChecklist {

    private final ModeloChecklistRepository modeloChecklistRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModeloEquipamentoRepository modeloEquipamentoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public ModeloChecklist cadastrar(ModeloChecklistCadastrarInputDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        validarNomeChecklistDisponivel(dto.nome(), usuario.getEnderecoId());

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

        List<ModeloEquipamentoChecklist> modelos = dto.modelos() == null
                ? Collections.emptyList()
                : dto.modelos().stream()
                .map(modelo -> construirModeloEquipamento(modelo, usuario))
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

    private void validarNomeChecklistDisponivel(String nome, Long enderecoId) {
        boolean duplicado = modeloChecklistRepository.findAll(enderecoId).stream()
                .anyMatch(modelo -> normalizar(modelo.getNome()).equals(normalizar(nome)));

        if (duplicado) {
            throw new RegraProblemaException("exception.modeloChecklist.duplicate");
        }
    }

    private String normalizar(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase(java.util.Locale.ROOT);
    }

    private ModeloEquipamentoChecklist construirModeloEquipamento(
            ModeloEquipamentoCadastrarInputDTO dto,
            Usuario usuario
    ) {
        ModeloEquipamento modelo = modeloEquipamentoRepository.findById(dto.modeloEquipamentoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound"));

        validarEndereco(modelo.getUsuario(), usuario);

        List<Long> equipamentoIds = dto.equipamentos().stream()
                .map(item -> {
                    Equipamento equipamento = equipamentoRepository.findById(item.equipamentoId())
                            .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.equipamento.notFound"));

                    if (equipamento.getModeloEquipamento() == null
                            || !modelo.getId().equals(equipamento.getModeloEquipamento().getId())) {
                        throw new RegraProblemaException("exception.equipamento.modelo.invalid");
                    }

                    validarEndereco(equipamento.getModeloEquipamento().getUsuario(), usuario);
                    return equipamento.getId();
                })
                .toList();

        return new ModeloEquipamentoChecklist(modelo.getId(), equipamentoIds);
    }

    private void validarEndereco(Usuario proprietario, Usuario usuario) {
        if (proprietario == null || !usuario.getEnderecoId().equals(proprietario.getEnderecoId())) {
            throw new RegraProblemaException("exception.endereco.required");
        }
    }

}
