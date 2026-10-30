package org.example.segundoapinosql.infrastructure.database.sql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.segundoapinosql.domain.enums.TipoAcesso;
import org.example.segundoapinosql.domain.enums.converter.TipoAcessoConverter;

@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private UnidadeEntity unidade;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "email")
    private String email;

    @jakarta.persistence.Convert(converter = TipoAcessoConverter.class)
    @Column(name = "tipo_acesso")
    private TipoAcesso tipoAcesso;
}
