package org.example.segundoapinosql.adapters.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.controller.contract.ModeloChecklistControllerContract;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistAtualizarInputDTO;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistCadastrarInputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloChecklistOutputDTO;
import org.example.segundoapinosql.adapters.mapper.ModeloChecklistMapper;
import org.example.segundoapinosql.adapters.utils.ControllerUtils;
import org.example.segundoapinosql.application.usecase.ModeloChecklist.AtualizarModeloChecklist;
import org.example.segundoapinosql.application.usecase.ModeloChecklist.CadastrarModeloChecklist;
import org.example.segundoapinosql.application.usecase.ModeloChecklist.ListarModeloChecklist;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.Control;
import java.util.List;

@RestController
@RequestMapping("/api/v1/modelo-checklist")
@RequiredArgsConstructor
public class ModeloChecklistController implements ModeloChecklistControllerContract {

    // UseCases
    private final ListarModeloChecklist listarModeloChecklist;
    private final CadastrarModeloChecklist cadastrarModeloChecklist;
    private final AtualizarModeloChecklist atualizarModeloChecklist;

    // Mapper
    private final ModeloChecklistMapper mapper;

    // GET
    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<ModeloChecklistOutputDTO>> listar(
            Authentication authentication
    ) {
        return ResponseEntity.ok(toOutputDTO(
                listarModeloChecklist.listar(ControllerUtils.usuarioId(authentication))
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
                    dto,
                        ControllerUtils.usuarioId(authentication)
                )));
    }

    @Override
    @PatchMapping("/atualizar")
    public ResponseEntity<ModeloChecklistOutputDTO> atualizar(
            @Valid @RequestBody ModeloChecklistAtualizarInputDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(atualizarModeloChecklist.atualizar(
                dto,
                ControllerUtils.usuarioId(authentication)
        )));
    }

    // Mapper par DTO de saída em Lote
    private List<ModeloChecklistOutputDTO> toOutputDTO(List<ModeloChecklist> categoriasEquipamento) {
        return categoriasEquipamento.stream().map(mapper::toOutputDTO).toList();
    }

}
