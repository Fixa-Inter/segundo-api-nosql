package org.example.segundoapinosql.domain.repository;

import java.util.Optional;
import org.example.segundoapinosql.domain.model.ModeloEquipamento;

public interface ModeloEquipamentoRepository {
    Optional<ModeloEquipamento> findById(Long id);
}
