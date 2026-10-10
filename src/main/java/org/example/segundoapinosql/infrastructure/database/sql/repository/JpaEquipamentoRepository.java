package org.example.segundoapinosql.infrastructure.database.sql.repository;

import org.example.segundoapinosql.infrastructure.database.sql.entity.EquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEquipamentoRepository extends JpaRepository<EquipamentoEntity, Long> {
}
