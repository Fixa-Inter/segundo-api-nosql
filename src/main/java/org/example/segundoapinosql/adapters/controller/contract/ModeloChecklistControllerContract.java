package org.example.segundoapinosql.adapters.controller.contract;

import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistAtualizarInputDTO;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistCadastrarInputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloChecklistOutputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ModeloChecklistControllerContract {

    ResponseEntity<ModeloChecklistOutputDTO> cadastrar(
            ModeloChecklistCadastrarInputDTO dto,
            Authentication authentication
    );

    ResponseEntity<List<ModeloChecklistOutputDTO>> listar(
            Authentication authentication
    );

    ResponseEntity<ModeloChecklistOutputDTO> atualizar(
            ModeloChecklistAtualizarInputDTO dto,
            Authentication authentication
    );

    ResponseEntity<ModeloChecklistOutputDTO> deletar(
            String modeloChecklistId,
            Authentication authentication
    );

}
