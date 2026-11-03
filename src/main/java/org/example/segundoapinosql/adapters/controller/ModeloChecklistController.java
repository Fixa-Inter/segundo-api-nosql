package org.example.segundoapinosql.adapters.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistAtualizarInputDTO;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistCadastrarInputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloChecklistOutputDTO;
import org.example.segundoapinosql.adapters.mapper.ModeloChecklistMapper;
import org.example.segundoapinosql.adapters.utils.ControllerUtils;
import org.example.segundoapinosql.application.usecase.ModeloChecklist.AtualizarModeloChecklist;
import org.example.segundoapinosql.application.usecase.ModeloChecklist.CadastrarModeloChecklist;
import org.example.segundoapinosql.application.usecase.ModeloChecklist.DeletarModeloChecklist;
import org.example.segundoapinosql.application.usecase.ModeloChecklist.ListarModeloChecklist;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.input.ModeloChecklistCadastroDados;
import org.example.segundoapinosql.domain.model.input.ModeloChecklistAtualizacaoDados;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/modelo-checklist")
@RequiredArgsConstructor
public class ModeloChecklistController extends org.example.segundoapinosql.infrastructure.swagger.ModeloChecklistSwagger {

    // UseCases
    private final ListarModeloChecklist listarModeloChecklist;
    private final CadastrarModeloChecklist cadastrarModeloChecklist;
    private final AtualizarModeloChecklist atualizarModeloChecklist;
    private final DeletarModeloChecklist deletarModeloChecklist;

    // Mapper
    private final ModeloChecklistMapper mapper;

    // GET
    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<ModeloChecklistOutputDTO>> listar(
            Authentication authentication
    ) {
        return ResponseEntity.ok(toOutputDTO(
                listarModeloChecklist.listar(ControllerUtils.usuarioId(authentication),
                        ControllerUtils.tipoAcesso(authentication))
        ));
    }

    // POST
    @Override
    @PostMapping("/cadastrar")
    public ResponseEntity<ModeloChecklistOutputDTO> cadastrar(
           @Valid @RequestBody ModeloChecklistCadastrarInputDTO dto,

           Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toOutputDTO(cadastrarModeloChecklist.cadastrar(
                    toCadastroDados(dto),
                        ControllerUtils.usuarioId(authentication),
                        ControllerUtils.tipoAcesso(authentication)
                )));
    }

    // PATCH
    @Override
    @PatchMapping("/atualizar")
    public ResponseEntity<ModeloChecklistOutputDTO> atualizar(
            @Valid @RequestBody ModeloChecklistAtualizarInputDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(atualizarModeloChecklist.atualizar(
                toAtualizacaoDados(dto),
                ControllerUtils.usuarioId(authentication),
                ControllerUtils.tipoAcesso(authentication)
        )));
    }

    // DELETE
    @Override
    @DeleteMapping("/deletar/{modeloChecklistId}")
    public ResponseEntity<ModeloChecklistOutputDTO> deletar(
            @PathVariable String modeloChecklistId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(deletarModeloChecklist.deletar(
                modeloChecklistId,
                ControllerUtils.usuarioId(authentication),
                ControllerUtils.tipoAcesso(authentication)
        )));
    }

    // Mapper par DTO de saída em Lote
    private List<ModeloChecklistOutputDTO> toOutputDTO(List<ModeloChecklist> categoriasEquipamento) {
        return categoriasEquipamento.stream().map(mapper::toOutputDTO).toList();
    }

    private ModeloChecklistCadastroDados toCadastroDados(ModeloChecklistCadastrarInputDTO dto) {
        List<ModeloChecklistCadastroDados.Campo> campos = dto.campos().stream()
                .map(campo -> new ModeloChecklistCadastroDados.Campo(
                        campo.nome(), campo.tipoCampo(), campo.obrigatorio(),
                        campo.opcoes() == null ? Collections.emptyList() : campo.opcoes().stream()
                                .map(opcao -> new ModeloChecklistCadastroDados.Opcao(opcao.nome(), opcao.obrigatorio()))
                                .toList()))
                .toList();
        List<ModeloChecklistCadastroDados.ModeloEquipamento> modelos = dto.modelos() == null
                ? null
                : dto.modelos().stream().map(modelo -> new ModeloChecklistCadastroDados.ModeloEquipamento(
                        modelo.modeloEquipamentoId(), modelo.equipamentos() == null ? List.of()
                                : modelo.equipamentos().stream().map(item ->
                                new ModeloChecklistCadastroDados.Equipamento(item.equipamentoId())).toList()))
                .toList();
        return new ModeloChecklistCadastroDados(dto.nome(), dto.periodicidade(), dto.primeiraAbertura(),
                dto.obrigatorio(), campos, modelos);
    }

    private ModeloChecklistAtualizacaoDados toAtualizacaoDados(ModeloChecklistAtualizarInputDTO dto) {
        List<ModeloChecklistAtualizacaoDados.Campo> campos = dto.campos() == null ? null
                : dto.campos().stream().map(campo -> new ModeloChecklistAtualizacaoDados.Campo(
                        campo.id(), campo.nome(), campo.tipoCampo(), campo.obrigatorio(),
                        campo.opcoes() == null ? null : campo.opcoes().stream().map(opcao ->
                                new ModeloChecklistAtualizacaoDados.Opcao(
                                        opcao.id(), opcao.nome(), opcao.obrigatorio())).toList()))
                .toList();
        List<ModeloChecklistAtualizacaoDados.ModeloEquipamento> modelos = dto.modelos() == null ? null
                : dto.modelos().stream().map(modelo -> new ModeloChecklistAtualizacaoDados.ModeloEquipamento(
                        modelo.modeloEquipamentoId(), modelo.equipamentos() == null ? null
                                : modelo.equipamentos().stream().map(item ->
                                new ModeloChecklistAtualizacaoDados.Equipamento(item.equipamentoId())).toList()))
                .toList();
        return new ModeloChecklistAtualizacaoDados(dto.id(), dto.nome(), dto.periodicidade(),
                dto.primeiraAbertura(), dto.obrigatorio(), campos, modelos);
    }

}
