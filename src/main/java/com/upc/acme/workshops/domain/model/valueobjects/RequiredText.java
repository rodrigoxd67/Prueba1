package com.upc.acme.workshops.domain.model.valueobjects;

import com.upc.acme.workshops.domain.exceptions.WorkshopRuleException;

public final class RequiredText {
    private RequiredText() { }

    public static String validate(String value, int max, String key) {
        if (value == null || value.isBlank() || value.strip().length() > max)
            throw new WorkshopRuleException(key);
        return value.strip();
    }
}
