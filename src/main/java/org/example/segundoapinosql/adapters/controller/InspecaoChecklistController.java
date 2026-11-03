package org.example.segundoapinosql.adapters.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.dto.input.InspecionarChecklist.InspecaoChecklistCadastrarInputDTO;
import org.example.segundoapinosql.adapters.dto.output.InspecaoChecklistOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloEquipamentoOutputDTO;
import org.example.segundoapinosql.adapters.mapper.ModeloChecklistMapper;
import org.example.segundoapinosql.adapters.mapper.InspecaoChecklistOutputMapper;
import org.example.segundoapinosql.adapters.utils.ControllerUtils;
import org.example.segundoapinosql.application.usecase.InspecaoChecklist.CadastrarInspecaoChecklist;
import org.example.segundoapinosql.application.usecase.InspecaoChecklist.ListarModelosEquipamento;
import org.example.segundoapinosql.domain.model.input.InspecaoChecklistCadastroDados;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inspecao-checklist")
@RequiredArgsConstructor
public class InspecaoChecklistController extends org.example.segundoapinosql.infrastructure.swagger.InspecaoChecklistSwagger {

    // UseCases
    private final ListarModelosEquipamento listarModelosEquipamento;
    private final CadastrarInspecaoChecklist cadastrarInspecaoChecklist;

    // Mapper
    private final ModeloChecklistMapper mapper;
    private final InspecaoChecklistOutputMapper inspecaoMapper;

    // GET
    @Override
    @GetMapping("/modelos/{modeloChecklistId}")
    public ResponseEntity<List<ModeloEquipamentoOutputDTO>> listarModelos(
            @PathVariable String modeloChecklistId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                listarModelosEquipamento.listar(modeloChecklistId, ControllerUtils.usuarioId(authentication),
                        ControllerUtils.tipoAcesso(authentication))
                .stream().map(mapper::toOutputDTO).toList()
        );
    }

    //POST
    @Override
    @PostMapping("/cadastrar")
    public ResponseEntity<InspecaoChecklistOutputDTO> cadastrar(
            @Valid @RequestBody InspecaoChecklistCadastrarInputDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inspecaoMapper.toOutputDTO(cadastrarInspecaoChecklist.cadastrar(
                        new InspecaoChecklistCadastroDados(
                                dto.modeloChecklistId(),
                                dto.equipamentoId(),
                                dto.dados()
                        ),
                        ControllerUtils.usuarioId(authentication),
                        ControllerUtils.tipoAcesso(authentication)
                )));
    }

}
