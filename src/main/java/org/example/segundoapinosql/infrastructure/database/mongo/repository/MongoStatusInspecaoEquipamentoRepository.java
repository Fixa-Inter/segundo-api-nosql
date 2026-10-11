package org.example.segundoapinosql.infrastructure.database.mongo.repository;

import org.example.segundoapinosql.infrastructure.database.mongo.document.StatusInspecaoEquipamentoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MongoStatusInspecaoEquipamentoRepository extends MongoRepository<StatusInspecaoEquipamentoDocument, String> {

    Optional<StatusInspecaoEquipamentoDocument> findByEquipamentoId(Long equipamentoId);

}
