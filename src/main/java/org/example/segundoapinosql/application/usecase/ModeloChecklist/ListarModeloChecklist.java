package org.example.segundoapinosql.application.usecase.ModeloChecklist;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;
import org.example.segundoapinosql.infrastructure.exception.EntidadeNaoEncontradaException;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarModeloChecklist {

    private final ModeloChecklistRepository modeloChecklistRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ModeloChecklist> listar(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("exception.usuario.required"));

        List<ModeloChecklist> modelos = modeloChecklistRepository.findAll(usuario.getEnderecoId());
        if (modelos.isEmpty()) throw new EntidadeNaoEncontradaException("exception.modeloChecklist.notFound");

        return modelos;
    }

}
