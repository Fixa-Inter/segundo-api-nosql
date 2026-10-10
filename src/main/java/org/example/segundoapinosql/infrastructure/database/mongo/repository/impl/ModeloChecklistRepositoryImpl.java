package org.example.segundoapinosql.infrastructure.database.mongo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.segundoapinosql.adapters.mapper.ModeloChecklistMapper;
import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.repository.ModeloChecklistRepository;
import org.example.segundoapinosql.infrastructure.database.mongo.repository.MongoModeloChecklistRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ModeloChecklistRepositoryImpl implements ModeloChecklistRepository {

    private final MongoModeloChecklistRepository mongoRepository;
    private final ModeloChecklistMapper mapper;

    @Override
    public ModeloChecklist save(ModeloChecklist modeloChecklist) {
        return mapper.toModel(
                mongoRepository.save(mapper.toDocument(modeloChecklist))
        );
    }

    @Override
    public List<ModeloChecklist> findAll(Long usuarioId) {
        return mongoRepository.findAllByEnderecoId(usuarioId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<ModeloChecklist> findById(String modeloChecklistId) {
        return mongoRepository
                .findById(modeloChecklistId)
                .map(mapper::toModel);
    }

    @Override
    public void deleteById(String modeloEquipamentoId) {
        mongoRepository.deleteById(modeloEquipamentoId);
    }
}
