package org.example.segundoapinosql.infrastructure.database.mongo.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "status_inspecao_equipamento")
public class StatusInspecaoEquipamentoDocument {

    @Id
    private String id;

    private Long equipamentoId;
    private String modeloChecklistId;
    private LocalDate dataUltimaInspecao;
    private LocalDate prazoInspecao;
    private Boolean status;
}
