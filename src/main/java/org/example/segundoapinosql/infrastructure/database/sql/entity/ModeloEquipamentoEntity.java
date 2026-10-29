package org.example.segundoapinosql.infrastructure.database.sql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "ModeloEquipamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeloEquipamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_equipamento_id")
    private MarcaEquipamentoEntity marcaEquipamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_equipamento_id")
    private CategoriaEquipamentoEntity categoriaEquipamento;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
