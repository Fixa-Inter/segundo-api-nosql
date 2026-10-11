package org.example.segundoapinosql.infrastructure.database.mongo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.mapper.StatusInspecaoEquipamentoMapper;
import org.example.segundoapinosql.domain.model.StatusInspecaoEquipamento;
import org.example.segundoapinosql.domain.repository.StatusInspecaoEquipamentoRepository;
import org.example.segundoapinosql.infrastructure.database.mongo.repository.MongoStatusInspecaoEquipamentoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StatusInspecaoEquipamentoRepositoryImpl implements StatusInspecaoEquipamentoRepository {

    private final MongoStatusInspecaoEquipamentoRepository repository;
    private final StatusInspecaoEquipamentoMapper mapper;

    @Override
    public StatusInspecaoEquipamento save(StatusInspecaoEquipamento status) {
        return mapper.toModel(repository.save(mapper.toDocument(status)));
    }

    @Override
    public Optional<StatusInspecaoEquipamento> findByEquipamentoId(Long equipamentoId) {
        return repository
                .findByEquipamentoId(equipamentoId)
                .map(mapper::toModel);
    }
}
