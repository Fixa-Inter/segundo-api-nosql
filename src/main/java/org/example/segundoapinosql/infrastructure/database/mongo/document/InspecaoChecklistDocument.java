package org.example.segundoapinosql.infrastructure.database.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inspecoes_checklist")
public class InspecaoChecklistDocument {

    @Id
    private String id;

    private String modeloChecklistId;
    private Long equipamentoId;
    private LocalDate dataInspecao;
    private java.util.Map<String, Object> dados;
}
