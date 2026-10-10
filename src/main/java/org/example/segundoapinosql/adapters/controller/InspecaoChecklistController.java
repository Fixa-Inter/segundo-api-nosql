package org.example.segundoapinosql.adapters.controller;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.controller.contract.InspecaoChecklistControllerContract;
import org.example.segundoapinosql.adapters.dto.output.EquipamentoOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloEquipamentoOutputDTO;
import org.example.segundoapinosql.adapters.mapper.ModeloChecklistMapper;
import org.example.segundoapinosql.adapters.utils.ControllerUtils;
import org.example.segundoapinosql.application.usecase.InspecaoChecklist.ListarEquipamentos;
import org.example.segundoapinosql.application.usecase.InspecaoChecklist.ListarModelosEquipamento;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inspecao-checklist")
@RequiredArgsConstructor
public class InspecaoChecklistController implements InspecaoChecklistControllerContract {

    private final ListarModelosEquipamento listarModelosEquipamento;
    private final ListarEquipamentos listarEquipamentos;

    private final ModeloChecklistMapper mapper;

    @Override
    @GetMapping("/modelos/{modeloChecklistId}")
    public ResponseEntity<List<ModeloEquipamentoOutputDTO>> listarModelos(
            @PathVariable String modeloChecklistId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                listarModelosEquipamento.listar(modeloChecklistId, ControllerUtils.usuarioId(authentication))
                .stream().map(mapper::toOutputDTO).toList()
        );
    }

    @Override
    @GetMapping("/modelos/{modeloChecklistId}/{modeloEquipamentoId}/equipamentos")
    public ResponseEntity<List<EquipamentoOutputDTO>> listarEquipamentos(
            @PathVariable String modeloChecklistId,
            @PathVariable Long modeloEquipamentoId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                listarEquipamentos.listar(modeloChecklistId, modeloEquipamentoId, ControllerUtils.usuarioId(authentication))
                .stream().map(mapper::toEquipamentoOutputDTO).toList());
    }
}
