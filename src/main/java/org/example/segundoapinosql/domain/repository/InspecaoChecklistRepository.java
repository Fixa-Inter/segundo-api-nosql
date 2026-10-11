package org.example.segundoapinosql.domain.repository;

import org.example.segundoapinosql.domain.model.InspecaoChecklist;

import java.util.Optional;

public interface InspecaoChecklistRepository {

    InspecaoChecklist save(InspecaoChecklist inspecaoChecklist);

    Optional<InspecaoChecklist> findByEquipamentoId(Long equipamentoId);

}
