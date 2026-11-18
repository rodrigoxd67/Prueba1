package com.upc.acme.workshops.domain.model.valueobjects;

import com.upc.acme.workshops.domain.exceptions.WorkshopRuleException;

public record PhoneNumber(String value) {
    public PhoneNumber {
        value = RequiredText.validate(value, 16, "phone.invalid");
        if (!value.matches("^[+][1-9][0-9]{1,14}$"))
            throw new WorkshopRuleException("phone.invalid");
    }
}
