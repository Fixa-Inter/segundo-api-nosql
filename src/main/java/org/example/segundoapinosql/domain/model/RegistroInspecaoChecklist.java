package org.example.segundoapinosql.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroInspecaoChecklist {
    private LocalDate dataInspecao;
    private List<Map<String, Object>> dados;

}
