package com.upc.acme.workshops.infrastructure.persistence.jpa.converters;

import com.upc.acme.workshops.domain.model.valueobjects.EmailAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmailAddressPersistenceConverter implements AttributeConverter<EmailAddress, String> {
    @Override public String convertToDatabaseColumn(EmailAddress attribute) {
        return attribute == null ? null : attribute.value();
    }
    @Override public EmailAddress convertToEntityAttribute(String value) {
        return value == null ? null : new EmailAddress(value);
    }
}
