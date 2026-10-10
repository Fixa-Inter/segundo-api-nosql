package org.example.segundoapinosql.application.usecase.ModeloChecklist;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.dto.input.ModeloChecklist.ModeloChecklistAtualizarInputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloChecklistOutputDTO;
import org.example.segundoapinosql.application.annotation.UseCase;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.Usuario;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.domain.repository.UsuarioRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class AtualizarModeloChecklist {

    private final ModeloChecklistRepository modeloChecklistRepository;
    private final UsuarioRepository usuarioRepository;

    public ModeloChecklist atualizar(
            ModeloChecklistAtualizarInputDTO dto,
            Long usuarioId
    ) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("validation.usuario.required"));

        ModeloChecklist modeloChecklist = modeloChecklistRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("exception.modeloChecklist.notFound"));

        if (dto.nome() != null) modeloChecklist.setNome(dto.nome());
        if (dto.periodicidade() != null) modeloChecklist.setPeriodicidade(dto.periodicidade());
        if (dto.primeiraAbertura() != null) modeloChecklist.setPrimeiraAbertura(dto.primeiraAbertura());
        if (dto.obrigatorio() != null) modeloChecklist.setObrigatorio(dto.obrigatorio());

        modeloChecklistRepository.save(modeloChecklist);
        return modeloChecklist;
    }
}
