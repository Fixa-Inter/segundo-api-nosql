package org.example.segundoapinosql.infrastructure.database.mongo.document;

import org.example.segundoapinosql.domain.model.RegistroInspecaoChecklist;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "inpecoes_checklist")
public class InspecaoChecklistDocument {

    @Id
    private String id;

    private Long modeloEquipamentoChecklistId;
    private Long equipamentoId;
    private LocalDate dataAtualizacao;
    private LocalDate prazoInspecao;
    private Boolean status;
    private List<RegistroInspecaoChecklist> registros;
}
