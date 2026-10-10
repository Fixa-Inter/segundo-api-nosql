package org.example.segundoapinosql.infrastructure.database.mongo.repository;

import org.example.segundoapinosql.infrastructure.database.mongo.document.InspecaoChecklistDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoInspecaoChecklistRepository extends MongoRepository<InspecaoChecklistDocument, String> {}
