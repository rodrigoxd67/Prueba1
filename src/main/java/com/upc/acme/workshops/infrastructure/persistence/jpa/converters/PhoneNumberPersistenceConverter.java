package com.upc.acme.workshops.infrastructure.persistence.jpa.converters;

import com.upc.acme.workshops.domain.model.valueobjects.PhoneNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PhoneNumberPersistenceConverter implements AttributeConverter<PhoneNumber, String> {
    @Override public String convertToDatabaseColumn(PhoneNumber attribute) {
        return attribute == null ? null : attribute.value();
    }
    @Override public PhoneNumber convertToEntityAttribute(String value) {
        return value == null ? null : new PhoneNumber(value);
    }
}
