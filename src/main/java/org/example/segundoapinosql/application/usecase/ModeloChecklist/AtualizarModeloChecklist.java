package org.example.segundoapinosql.application.usecase.ModeloChecklist;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.application.exception.EntidadeNaoEncontradaException;
import org.example.segundoapinosql.application.exception.RegraProblemaException;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.application.security.PermissaoChecklist;
import org.example.segundoapinosql.domain.model.Campo;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.ModeloEquipamento;
import org.example.segundoapinosql.domain.model.ModeloEquipamentoChecklist;
import org.example.segundoapinosql.domain.model.Opcao;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.enums.TipoAcesso;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.domain.repository.ModeloEquipamentoRepository;
import org.example.segundoapinosql.domain.repository.EquipamentoRepository;
import org.example.segundoapinosql.domain.model.input.ModeloChecklistAtualizacaoDados;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class AtualizarModeloChecklist {

    private final ModeloChecklistRepository modeloChecklistRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModeloEquipamentoRepository modeloEquipamentoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public ModeloChecklist atualizar(
            ModeloChecklistAtualizacaoDados dto,
            Long usuarioId,
            TipoAcesso tipoAcesso
    ) {
        PermissaoChecklist.exigirGestao(tipoAcesso);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.usuario.required"));

        ModeloChecklist modeloChecklist = modeloChecklistRepository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloChecklist.notFound"));

        if (!usuario.getUnidadeId().equals(modeloChecklist.getUnidadeId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.nome() != null) {
            validarNomeChecklistDisponivel(
                    dto.nome(),
                    modeloChecklist.getUnidadeId(),
                    modeloChecklist.getId()
            );
            modeloChecklist.setNome(dto.nome());
        }
        if (dto.periodicidade() != null) modeloChecklist.setPeriodicidade(dto.periodicidade());
        if (dto.primeiraAbertura() != null) modeloChecklist.setPrimeiraAbertura(dto.primeiraAbertura());
        if (dto.obrigatorio() != null) modeloChecklist.setObrigatorio(dto.obrigatorio());

        if (dto.modelos() != null) {
            modeloChecklist.setModelos(validarEConstruirModelos(dto.modelos(), usuario));
        }

        if (dto.campos() != null) modeloChecklist.setCampos(atualizarCampos(
                dto.campos(),
                modeloChecklist.getCampos()
        ));

        return modeloChecklistRepository.save(modeloChecklist);
    }

    private void validarNomeChecklistDisponivel(
            String nome,
            Long unidadeId,
            String idAtual
    ) {
        boolean duplicado = modeloChecklistRepository.findAll(unidadeId).stream()
                .anyMatch(modelo -> !Objects.equals(modelo.getId(), idAtual)
                        && normalizar(modelo.getNome()).equals(normalizar(nome)));

        if (duplicado) {
            throw new RegraProblemaException("exception.modeloChecklist.duplicate");
        }
    }

    private List<ModeloEquipamentoChecklist> validarEConstruirModelos(
            List<ModeloChecklistAtualizacaoDados.ModeloEquipamento> modelosDto,
            Usuario usuario
    ) {
        return modelosDto.stream()
                .map(modeloDto -> {
                    ModeloEquipamento modelo = modeloEquipamentoRepository
                            .findById(modeloDto.modeloEquipamentoId())
                            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                                    "exception.modeloEquipamento.notFound"
                            ));

                    validarUnidade(modelo, usuario);

                    List<Long> equipamentoIds = modeloDto.equipamentos() == null
                            ? List.of()
                            : modeloDto.equipamentos().stream()
                            .map(equipamentoDto -> {
                                var equipamento = equipamentoRepository
                                        .findById(equipamentoDto.equipamentoId())
                                        .orElseThrow(() -> new EntidadeNaoEncontradaException(
                                                "exception.equipamento.notFound"
                                        ));

                                if (equipamento.getModeloEquipamento() == null
                                        || !modelo.getId().equals(
                                        equipamento.getModeloEquipamento().getId())) {
                                    throw new RegraProblemaException(
                                            "exception.equipamento.modelo.invalid"
                                    );
                                }

                                validarUnidade(equipamento.getUsuario(), usuario);
                                return equipamento.getId();
                            })
                            .toList();

                    return new ModeloEquipamentoChecklist(
                            modelo.getId(),
                            equipamentoIds
                    );
                })
                .toList();
    }

    private void validarUnidade(Usuario proprietario, Usuario usuario) {
        if (proprietario == null
                || !usuario.getUnidadeId().equals(proprietario.getUnidadeId())) {
            throw new RegraProblemaException("exception.unidade.required");
        }
    }

    private void validarUnidade(ModeloEquipamento modelo, Usuario usuario) {
        if (modelo.getCategoriaEquipamento() == null) {
            throw new RegraProblemaException("exception.unidade.required");
        }
        validarUnidade(modelo.getCategoriaEquipamento().getUsuario(), usuario);
    }

    private List<Campo> atualizarCampos(
            List<ModeloChecklistAtualizacaoDados.Campo> dtoCampos,
            List<Campo> campos
    ) {
        List<Campo> camposAtuais = campos == null ? Collections.emptyList() : campos;
        List<Campo> camposAtualizados = new ArrayList<>();

        for (ModeloChecklistAtualizacaoDados.Campo dtoCampo : dtoCampos) {
            Campo campoExistente = camposAtuais.stream()
                    .filter(campo -> Objects.equals(campo.getId(), dtoCampo.id()))
                    .findFirst()
                    .orElse(null);

            if (campoExistente == null) {
                validarNovoCampo(dtoCampo);
                validarNomeCampoDisponivel(dtoCampo.nome(), camposAtualizados, null);

                Campo novoCampo = new Campo(
                        UUID.randomUUID().toString(),
                        dtoCampo.nome(),
                        dtoCampo.tipoCampo(),
                        dtoCampo.obrigatorio(),
                        atualizarOpcoes(dtoCampo.opcoes(), Collections.emptyList())
                );
                camposAtualizados.add(novoCampo);
                continue;
            }

            validarNomeCampoDisponivel(dtoCampo.nome(), camposAtualizados, campoExistente.getId());

            if (dtoCampo.nome() != null) campoExistente.setNome(dtoCampo.nome());
            if (dtoCampo.tipoCampo() != null) campoExistente.setTipoCampo(dtoCampo.tipoCampo());
            if (dtoCampo.obrigatorio() != null) campoExistente.setObrigatorio(dtoCampo.obrigatorio());
            if (dtoCampo.opcoes() != null) {
                campoExistente.setOpcoes(atualizarOpcoes(
                        dtoCampo.opcoes(),
                        campoExistente.getOpcoes() == null
                                ? Collections.emptyList()
                                : campoExistente.getOpcoes()
                ));
            }
            camposAtualizados.add(campoExistente);
        }

        return camposAtualizados;
    }

    private List<Opcao> atualizarOpcoes (
            List<ModeloChecklistAtualizacaoDados.Opcao> dtoOpcoes,
            List<Opcao> opcoes
    ) {
        List<Opcao> opcoesAtuais = opcoes == null ? Collections.emptyList() : opcoes;
        List<Opcao> opcoesAtualizadas = new ArrayList<>();

        if (dtoOpcoes == null) {
            return opcoesAtualizadas;
        }

        for (ModeloChecklistAtualizacaoDados.Opcao dtoOpcao : dtoOpcoes) {
            Opcao opcaoExistente = opcoesAtuais.stream()
                    .filter(opcao -> Objects.equals(opcao.getId(), dtoOpcao.id()))
                    .findFirst()
                    .orElse(null);

            if (opcaoExistente == null) {
                validarNovaOpcao(dtoOpcao);
                validarNomeOpcaoDisponivel(dtoOpcao.nome(), opcoesAtualizadas, null);

                opcoesAtualizadas.add(new Opcao(
                        UUID.randomUUID().toString(),
                        dtoOpcao.nome(),
                        dtoOpcao.obrigatorio()
                ));
                continue;
            }

            validarNomeOpcaoDisponivel(dtoOpcao.nome(), opcoesAtualizadas, opcaoExistente.getId());

            if (dtoOpcao.nome() != null) opcaoExistente.setNome(dtoOpcao.nome());
            if (dtoOpcao.obrigatorio() != null) opcaoExistente.setObrigatorio(dtoOpcao.obrigatorio());
            opcoesAtualizadas.add(opcaoExistente);
        }

        return opcoesAtualizadas;
    }

    private void validarNovoCampo(ModeloChecklistAtualizacaoDados.Campo campo) {
        if (campo.nome() == null || campo.nome().isBlank()) {
            throw new IllegalArgumentException("validation.campo.nome.required");
        }

        if (campo.tipoCampo() == null) {
            throw new IllegalArgumentException("validation.campo.tipo.required");
        }
    }

    private void validarNovaOpcao(ModeloChecklistAtualizacaoDados.Opcao opcao) {
        if (opcao.nome() == null || opcao.nome().isBlank()) {
            throw new IllegalArgumentException("validation.opcao.nome.required");
        }
    }

    private void validarNomeCampoDisponivel(
            String nome,
            List<Campo> campos,
            String idAtual
    ) {
        if (nome == null) return;

        boolean duplicado = campos.stream()
                .anyMatch(campo -> !Objects.equals(campo.getId(), idAtual)
                        && normalizar(campo.getNome()).equals(normalizar(nome)));

        if (duplicado) {
            throw new RegraProblemaException("exception.checklist.field.duplicate");
        }
    }

    private void validarNomeOpcaoDisponivel(
            String nome,
            List<Opcao> opcoes,
            String idAtual
    ) {
        if (nome == null) return;

        boolean duplicado = opcoes.stream()
                .anyMatch(opcao -> !Objects.equals(opcao.getId(), idAtual)
                        && normalizar(opcao.getNome()).equals(normalizar(nome)));

        if (duplicado) {
            throw new RegraProblemaException("exception.checklist.option.duplicate");
        }
    }

    private String normalizar(String valor) {
        return valor == null
                ? ""
                : valor.trim().toLowerCase(Locale.ROOT);
    }
}
