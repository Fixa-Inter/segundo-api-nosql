package org.example.segundoapinosql.infrastructure.database.mongo.repository;

import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.infrastructure.database.mongo.document.ModeloChecklistDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoModeloChecklistRepository extends MongoRepository<ModeloChecklistDocument, String> {
    List<ModeloChecklistDocument> findAllByEnderecoId(Long enderecoId);
}
