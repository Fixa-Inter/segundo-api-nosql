package org.example.segundoapinosql.infrastructure.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.segundoapinosql.adapters.controller.contract.InspecaoChecklistControllerContract;
import org.example.segundoapinosql.adapters.dto.input.InspecionarChecklist.InspecaoChecklistCadastrarInputDTO;
import org.example.segundoapinosql.adapters.dto.output.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Inspeções", description = "Consulta de modelos, equipamentos e cadastro de inspeções.")
@SecurityRequirement(name = "bearerAuth")
public abstract class InspecaoChecklistSwagger implements InspecaoChecklistControllerContract {

    @Operation(summary = "Listar modelos do checklist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelos encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ModeloEquipamentoOutputDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Checklist não encontrado")
    })
    public abstract ResponseEntity<List<ModeloEquipamentoOutputDTO>> listarModelos(
            @Parameter(description = "ID do checklist", required = true, in = ParameterIn.PATH) String modeloChecklistId,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "Listar equipamentos do modelo no checklist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipamentos encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EquipamentoOutputDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Checklist ou modelo não encontrado")
    })
    public abstract ResponseEntity<List<EquipamentoOutputDTO>> listarEquipamentos(
            @Parameter(description = "ID do checklist", required = true, in = ParameterIn.PATH) String modeloChecklistId,
            @Parameter(description = "ID do modelo de equipamento", required = true, in = ParameterIn.PATH) Long modeloEquipamentoId,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "Cadastrar inspeção", description = "Registra uma inspeção e atualiza o status atual do equipamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inspeção cadastrada",
                    content = @Content(schema = @Schema(implementation = InspecaoChecklistOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados da inspeção inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Checklist ou equipamento não encontrado")
    })
    public abstract ResponseEntity<InspecaoChecklistOutputDTO> cadastrar(
            @RequestBody(required = true, description = "Dados da inspeção",
                    content = @Content(schema = @Schema(implementation = InspecaoChecklistCadastrarInputDTO.class)))
            InspecaoChecklistCadastrarInputDTO dto,
            @Parameter(hidden = true) Authentication authentication
    );
}
