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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo_equipamento_id")
    private ModeloEquipamentoEntity modeloEquipamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_unidade_id")
    private LocalUnidadeEntity localUnidade;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "data_criacao")
    private java.time.LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
