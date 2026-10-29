package org.example.segundoapinosql.infrastructure.database.sql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "MarcaEquipamento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MarcaEquipamentoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
