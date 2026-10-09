package org.example.segundoapinosql.infrastructure.database.mongo.repository;

import org.example.segundoapinosql.infrastructure.database.mongo.document.ModeloChecklistDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoModeloChecklistRepository extends MongoRepository<ModeloChecklistDocument, Long> {

}
