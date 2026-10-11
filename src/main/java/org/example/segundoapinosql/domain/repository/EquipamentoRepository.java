package org.example.segundoapinosql.domain.repository;

import java.util.Optional;
import java.util.List;
import org.example.segundoapinosql.domain.model.Equipamento;

public interface EquipamentoRepository {

    Optional<Equipamento> findById(Long id);

}
