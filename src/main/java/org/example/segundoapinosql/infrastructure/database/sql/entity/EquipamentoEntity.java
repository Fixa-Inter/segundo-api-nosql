package org.example.segundoapinosql.infrastructure.database.sql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Equipamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "modelo_equipamento_id", nullable = false)
    private ModeloEquipamentoEntity modeloEquipamento;

    @Column(name = "codigo", nullable = false)
    private String codigo;
}
