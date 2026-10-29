package org.example.segundoapinosql.infrastructure.database.sql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "CategoriaEquipamento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CategoriaEquipamentoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;
}
