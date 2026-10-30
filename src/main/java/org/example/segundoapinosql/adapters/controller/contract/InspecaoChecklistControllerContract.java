package org.example.segundoapinosql.adapters.controller.contract;

import org.example.segundoapinosql.adapters.dto.input.InspecionarChecklist.InspecaoChecklistCadastrarInputDTO;
import org.example.segundoapinosql.adapters.dto.output.InspecaoChecklistOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloEquipamentoOutputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.List;

public interface InspecaoChecklistControllerContract {

    ResponseEntity<List<ModeloEquipamentoOutputDTO>> listarModelos(
            String modeloChecklistId,
            Authentication authentication
    );

    ResponseEntity<InspecaoChecklistOutputDTO> cadastrar(
            InspecaoChecklistCadastrarInputDTO dto,
            Authentication authentication
    );

}
