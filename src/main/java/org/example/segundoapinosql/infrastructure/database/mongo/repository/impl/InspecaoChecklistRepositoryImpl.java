package org.example.segundoapinosql.infrastructure.database.mongo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.mapper.InspecaoChecklistMapper;
import org.example.segundoapinosql.domain.model.InspecaoChecklist;
import org.example.segundoapinosql.domain.repository.InspecaoChecklistRepository;
import org.example.segundoapinosql.infrastructure.database.mongo.document.InspecaoChecklistDocument;
import org.example.segundoapinosql.infrastructure.database.mongo.repository.MongoInspecaoChecklistRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InspecaoChecklistRepositoryImpl implements InspecaoChecklistRepository {

    private final MongoInspecaoChecklistRepository mongoRepository;
    private final InspecaoChecklistMapper mapper;

    @Override
    public Optional<InspecaoChecklist> findByEquipamentoId(Long equipamentoId) {
        Optional<InspecaoChecklistDocument> inspecaoChecklistDocument = Optional.ofNullable(
                mongoRepository.findByEquipamentoId(equipamentoId)
        );

        return inspecaoChecklistDocument.map(mapper::toModel);
    }
}
