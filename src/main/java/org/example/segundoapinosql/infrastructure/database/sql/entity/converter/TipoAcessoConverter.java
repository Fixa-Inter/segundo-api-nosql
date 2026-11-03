package org.example.segundoapinosql.infrastructure.database.sql.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.segundoapinosql.domain.enums.TipoAcesso;

/** Conversão de persistência do tipo de acesso entre SQL e o enum de domínio. */
@Converter(autoApply = true)
public class TipoAcessoConverter implements AttributeConverter<TipoAcesso, Integer> {
    @Override public Integer convertToDatabaseColumn(TipoAcesso attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override public TipoAcesso convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : TipoAcesso.fromId(dbData);
    }
}
