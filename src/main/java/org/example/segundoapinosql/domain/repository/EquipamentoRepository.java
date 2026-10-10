package org.example.segundoapinosql.domain.repository;

import java.util.Optional;
import org.example.segundoapinosql.domain.model.Equipamento;

public interface EquipamentoRepository {
    Optional<Equipamento> findById(Long id);
}
