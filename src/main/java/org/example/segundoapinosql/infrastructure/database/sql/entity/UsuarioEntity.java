package org.example.segundoapinosql.infrastructure.database.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.segundoapinosql.domain.enums.TipoAcesso;

@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "endereco_id", nullable = false)
    private Long enderecoId;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "tipo_acesso", nullable = false)
    private TipoAcesso tipoAcesso;
}
