package org.example.segundoapinosql.application.usecase.ModeloChecklist;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.infrastructure.exception.RegraProblemaException;

@UseCase
@RequiredArgsConstructor
public class DeletarModeloChecklist {

    private final ModeloChecklistRepository modeloChecklistRepository;
    private final UsuarioRepository usuarioRepository;

    public ModeloChecklist deletar(String modeloChecklistId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("validation.usuario.required"));

        ModeloChecklist modeloChecklist = modeloChecklistRepository.findById(modeloChecklistId)
                .orElseThrow(() -> new EntityNotFoundException("exception.modeloChecklist.notFound"));

        if (!usuario.getEnderecoId().equals(modeloChecklist.getEnderecoId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        modeloChecklistRepository.deleteById(modeloChecklistId);
        return modeloChecklist;
    }

}
