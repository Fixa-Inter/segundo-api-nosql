package org.example.segundoapinosql.adapters.controller.contract;

import jakarta.validation.Valid;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklistInputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloChecklistOutputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

public interface ModeloChecklistControllerContract {

    ResponseEntity<ModeloChecklistOutputDTO> cadastrar(
            ModeloChecklistInputDTO dto,
            Authentication authentication
    );

}
