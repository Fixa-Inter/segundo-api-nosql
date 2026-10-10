package org.example.segundoapinosql.infrastructure.database.sql.repository.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.mapper.EquipamentoMapper;
import org.example.segundoapinosql.domain.model.Equipamento;
import org.example.segundoapinosql.domain.repository.EquipamentoRepository;
import org.example.segundoapinosql.infrastructure.database.sql.repository.JpaEquipamentoRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EquipamentoRepositoryImpl implements EquipamentoRepository {
    private final JpaEquipamentoRepository repository;
    private final EquipamentoMapper mapper;

    @Override
    public Optional<Equipamento> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }
}
