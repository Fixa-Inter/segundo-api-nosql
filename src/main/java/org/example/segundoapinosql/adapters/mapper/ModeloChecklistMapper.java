package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.ModeloChecklist;
import org.example.segundoapinosql.domain.model.Campo;
import org.example.segundoapinosql.domain.model.Equipamento;
import org.example.segundoapinosql.domain.model.ModeloEquipamento;
import org.example.segundoapinosql.domain.model.Opcao;
import org.example.segundoapinosql.adapters.dto.output.CampoOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.EquipamentoOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloChecklistOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloEquipamentoOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.OpcaoOutputDTO;
import org.example.segundoapinosql.infrastructure.database.mongo.document.ModeloChecklistDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModeloChecklistMapper {

    ModeloChecklistDocument toDocument(ModeloChecklist model);
    ModeloChecklist toModel(ModeloChecklistDocument entity);

    ModeloChecklistOutputDTO toOutputDTO(ModeloChecklist model);

    @Mapping(source = "tipoCampo.nome", target = "tipoCampo")
    CampoOutputDTO toOutputDTO(Campo campo);

    OpcaoOutputDTO toOutputDTO(Opcao opcao);
    ModeloEquipamentoOutputDTO toOutputDTO(ModeloEquipamento modeloEquipamento);
    EquipamentoOutputDTO toOutputDTO(Equipamento equipamento);

}
