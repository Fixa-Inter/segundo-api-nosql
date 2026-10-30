package org.example.segundoapinosql.infrastructure.database.sql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Unidade")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UnidadeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
