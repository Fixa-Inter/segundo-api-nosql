package org.example.segundoapinosql.domain.repository;

import org.example.segundoapinosql.domain.model.StatusInspecaoEquipamento;
import java.util.Optional;
import java.util.List;

public interface StatusInspecaoEquipamentoRepository {

    StatusInspecaoEquipamento save(StatusInspecaoEquipamento status);

    Optional<StatusInspecaoEquipamento> findByEquipamentoId(Long equipamentoId);

    List<StatusInspecaoEquipamento> findAllByModeloChecklistId(String modeloChecklistId);

}
