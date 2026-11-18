package com.upc.acme.workshops.domain.model.valueobjects;

public record PersonName(String firstName, String lastName) {
    public PersonName {
        firstName = RequiredText.validate(firstName, 100, "manager.firstName.invalid");
        lastName = RequiredText.validate(lastName, 100, "manager.lastName.invalid");
    }
}
