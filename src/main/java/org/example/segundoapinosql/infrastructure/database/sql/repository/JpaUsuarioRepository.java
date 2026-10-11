package org.example.segundoapinosql.infrastructure.database.sql.repository;

import java.util.Optional;
import org.example.segundoapinosql.infrastructure.database.sql.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> { }
