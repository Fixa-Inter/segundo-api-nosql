package org.example.segundoapinosql.domain.repository;

import org.example.segundoapinosql.domain.model.StatusInspecaoEquipamento;
import java.util.Optional;

public interface StatusInspecaoEquipamentoRepository {

    StatusInspecaoEquipamento save(StatusInspecaoEquipamento status);

    Optional<StatusInspecaoEquipamento> findByEquipamentoId(Long equipamentoId);

}
