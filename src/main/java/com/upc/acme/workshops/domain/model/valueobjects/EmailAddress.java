package com.upc.acme.workshops.domain.model.valueobjects;

import com.upc.acme.workshops.domain.exceptions.WorkshopRuleException;
import java.util.regex.Pattern;

public record EmailAddress(String value) {
    private static final Pattern FORMAT = Pattern.compile(
            "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?\\.)+[A-Za-z]{2,63}$");

    public EmailAddress {
        value = RequiredText.validate(value, 254, "email.invalid");
        if (!FORMAT.matcher(value).matches() || value.indexOf('@') > 64)
            throw new WorkshopRuleException("email.invalid");
    }
}
