package org.example.segundoapinosql.adapters.mapper;

import org.example.segundoapinosql.domain.model.*;
import org.example.segundoapinosql.domain.repository.EquipamentoRepository;
import org.example.segundoapinosql.domain.repository.StatusInspecaoEquipamentoRepository;
import org.example.segundoapinosql.domain.repository.ModeloEquipamentoRepository;
import org.example.segundoapinosql.adapters.dto.output.CampoOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.EquipamentoOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloChecklistOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.ModeloEquipamentoOutputDTO;
import org.example.segundoapinosql.adapters.dto.output.OpcaoOutputDTO;
import org.example.segundoapinosql.infrastructure.database.mongo.document.ModeloChecklistDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ModeloChecklistMapper {

    @Autowired
    protected ModeloEquipamentoRepository modeloEquipamentoRepository;

    @Autowired
    protected EquipamentoRepository equipamentoRepository;

    @Autowired
    protected StatusInspecaoEquipamentoRepository statusInspecaoRepository;

    public abstract ModeloChecklistDocument toDocument(ModeloChecklist model);
    public abstract ModeloChecklist toModel(ModeloChecklistDocument entity);

    public ModeloChecklistOutputDTO toOutputDTO(ModeloChecklist model) {
        return new ModeloChecklistOutputDTO(
                model.getId(),
                model.getNome(),
                model.getPeriodicidade(),
                model.getCampos() == null ? Collections.emptyList() : model.getCampos().stream().map(this::toOutputDTO).toList(),
                model.getObrigatorio(),
                model.getPrimeiraAbertura(),
                model.getModelos() == null ? Collections.emptyList() : model.getModelos().stream().map(this::toOutputDTO).toList()
        );
    }

    @Mapping(source = "tipoCampo.nome", target = "tipoCampo")
    public abstract CampoOutputDTO toOutputDTO(Campo campo);

    public abstract OpcaoOutputDTO toOutputDTO(Opcao opcao);

    public ModeloEquipamentoOutputDTO toOutputDTO(ModeloEquipamentoChecklist modeloEquipamento) {
        ModeloEquipamento modelo = modeloEquipamentoRepository.findById(modeloEquipamento.getModeloEquipamentoId())
                .orElse(null);

        List<EquipamentoOutputDTO> equipamentos = modeloEquipamento.getEquipamentoIds() == null
                ? Collections.emptyList()
                : modeloEquipamento.getEquipamentoIds().stream()
                .map(equipamentoRepository::findById)
                .flatMap(java.util.Optional::stream)
                .map(this::toEquipamentoOutputDTO)
                .toList();

        return new ModeloEquipamentoOutputDTO(
                modeloEquipamento.getModeloEquipamentoId(),
                modelo == null ? null : modelo.getNome(),
                equipamentos
        );
    }

    public EquipamentoOutputDTO toEquipamentoOutputDTO(org.example.segundoapinosql.domain.model.Equipamento equipamento) {
        StatusInspecaoEquipamento inspecao = statusInspecaoRepository.findByEquipamentoId(equipamento.getId()).orElse(null);
        return new EquipamentoOutputDTO(
                equipamento.getId(), equipamento.getCodigo(),
                inspecao == null ? null : inspecao.getPrazoInspecao(),
                inspecao != null && Boolean.TRUE.equals(inspecao.getStatus())
        );
    }

}
