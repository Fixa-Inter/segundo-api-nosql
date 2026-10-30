package org.example.segundoapinosql.infrastructure.database.sql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Equipamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "modelo_equipamento_id")
    private ModeloEquipamentoEntity modeloEquipamento;

    @ManyToOne
    @JoinColumn(name = "local_unidade_id")
    private LocalUnidadeEntity localUnidade;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
