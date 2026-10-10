package org.example.segundoapinosql.domain.repository;

import org.example.segundoapinosql.domain.model.ModeloChecklist;

import java.util.List;
import java.util.Optional;

public interface ModeloChecklistRepository {

    ModeloChecklist save(ModeloChecklist modeloChecklist);

    List<ModeloChecklist> findAll(Long usuarioId);

    Optional<ModeloChecklist> findById(String modeloChecklistId);
}
