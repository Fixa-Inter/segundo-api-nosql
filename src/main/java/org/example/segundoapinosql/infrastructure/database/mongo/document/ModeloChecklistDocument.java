package org.example.segundoapinosql.infrastructure.database.mongo.document;

import org.example.segundoapinosql.domain.model.Campo;
import org.example.segundoapinosql.domain.model.ModeloEquipamentoChecklist;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "modelos_checklist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeloChecklistDocument {

    @Id
    private String id;

    private Long usuarioId;
    private Long enderecoId;
    private String nome;
    private Integer periodicidade;
    private List<Campo> campos;
    private Boolean obrigatorio;
    private LocalDate primeiraAbertura;
    private List<ModeloEquipamentoChecklist> modelos;
}
