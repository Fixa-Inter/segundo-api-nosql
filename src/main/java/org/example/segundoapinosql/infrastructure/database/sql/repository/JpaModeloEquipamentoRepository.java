package org.example.segundoapinosql.infrastructure.database.sql.repository;

import org.example.segundoapinosql.infrastructure.database.sql.entity.ModeloEquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaModeloEquipamentoRepository extends JpaRepository<ModeloEquipamentoEntity, Long> {
}
