package org.example.segundoapinosql.infrastructure.database.mongo.document;

import jakarta.persistence.Id;
import org.example.segundoapinosql.domain.model.Campo;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "modelos_checklist")
public class ModeloChecklistDocument {

    @Id
    private Long id;

    private Long enderecoId;
    private String nome;
    private Integer periodicidade;
    private List<Campo> campos;
    private Boolean obrigatorio;
    private LocalDate primeiraAbertura;
}
