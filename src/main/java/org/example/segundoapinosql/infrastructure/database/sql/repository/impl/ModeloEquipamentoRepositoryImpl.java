package org.example.segundoapinosql.infrastructure.database.sql.repository.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.mapper.ModeloEquipamentoMapper;
import org.example.segundoapinosql.domain.model.ModeloEquipamento;
import org.example.segundoapinosql.domain.repository.ModeloEquipamentoRepository;
import org.example.segundoapinosql.infrastructure.database.sql.repository.JpaModeloEquipamentoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ModeloEquipamentoRepositoryImpl implements ModeloEquipamentoRepository {
    private final JpaModeloEquipamentoRepository repository;
    private final ModeloEquipamentoMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<ModeloEquipamento> findById(Long id) {

        return repository
                .findById(id)
                .map(mapper::toModel);
    }
}
