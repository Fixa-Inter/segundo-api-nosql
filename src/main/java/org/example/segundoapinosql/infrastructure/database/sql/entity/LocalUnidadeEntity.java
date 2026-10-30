package org.example.segundoapinosql.infrastructure.database.sql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "LocalUnidade")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LocalUnidadeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
