package org.example.segundoapinosql.infrastructure.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.segundoapinosql.adapters.controller.contract.ModeloChecklistControllerContract;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistAtualizarInputDTO;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistCadastrarInputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloChecklistOutputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Modelos de checklist", description = "Cadastro, consulta, atualização e remoção de modelos de checklist.")
@SecurityRequirement(name = "bearerAuth")
public abstract class ModeloChecklistSwagger implements ModeloChecklistControllerContract {

    @Operation(summary = "Cadastrar modelo de checklist")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201", description = "Modelo cadastrado",
                            content = @Content(schema = @Schema(implementation = ModeloChecklistOutputDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado")
            })
    public abstract ResponseEntity<ModeloChecklistOutputDTO> cadastrar(
            @RequestBody(required = true, description = "Dados do modelo de checklist",
                    content = @Content(schema = @Schema(implementation = ModeloChecklistCadastrarInputDTO.class)))
            ModeloChecklistCadastrarInputDTO dto,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "Listar modelos de checklist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelos encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ModeloChecklistOutputDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<List<ModeloChecklistOutputDTO>> listar(@Parameter(hidden = true) Authentication authentication);

    @Operation(summary = "Atualizar modelo de checklist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelo atualizado",
                    content = @Content(schema = @Schema(implementation = ModeloChecklistOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    public abstract ResponseEntity<ModeloChecklistOutputDTO> atualizar(
            @RequestBody(required = true, description = "Dados atualizados do modelo",
                    content = @Content(schema = @Schema(implementation = ModeloChecklistAtualizarInputDTO.class)))
            ModeloChecklistAtualizarInputDTO dto,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "Excluir modelo de checklist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelo removido",
                    content = @Content(schema = @Schema(implementation = ModeloChecklistOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    public abstract ResponseEntity<ModeloChecklistOutputDTO> deletar(
            @Parameter(description = "ID do modelo de checklist", required = true, in = ParameterIn.PATH)
            String modeloChecklistId,
            @Parameter(hidden = true) Authentication authentication);
}
