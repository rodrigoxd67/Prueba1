package com.upc.acme.workshops.infrastructure.persistence.jpa.converters;

import com.upc.acme.workshops.domain.model.valueobjects.DaimlerWorkshopId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.UUID;

@Converter
public class DaimlerWorkshopIdPersistenceConverter implements AttributeConverter<DaimlerWorkshopId, String> {
    @Override public String convertToDatabaseColumn(DaimlerWorkshopId attribute) {
        return attribute == null ? null : attribute.value().toString();
    }
    @Override public DaimlerWorkshopId convertToEntityAttribute(String value) {
        return value == null ? null : new DaimlerWorkshopId(UUID.fromString(value));
    }
}
