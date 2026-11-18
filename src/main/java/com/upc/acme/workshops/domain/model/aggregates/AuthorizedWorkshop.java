package com.upc.acme.workshops.domain.model.aggregates;

import com.upc.acme.workshops.domain.model.valueobjects.*;
import com.upc.acme.workshops.domain.exceptions.WorkshopRuleException;
import java.time.Instant;
import java.util.Objects;

public record AuthorizedWorkshop(Integer id, DaimlerWorkshopId workshopId, PersonName managerName,
        PhoneNumber contactPhone, EmailAddress contactEmail, String workshopLegalName,
        String city, String provinceOrState, String countryOrRegion,
        String encryptedAuthCode, Instant createdAt, Instant updatedAt) {
    public AuthorizedWorkshop {
        Objects.requireNonNull(workshopId);
        Objects.requireNonNull(managerName);
        Objects.requireNonNull(contactPhone);
        Objects.requireNonNull(contactEmail);
        workshopLegalName = RequiredText.validate(workshopLegalName, 200, "legalName.invalid");
        city = RequiredText.validate(city, 100, "city.invalid");
        countryOrRegion = RequiredText.validate(countryOrRegion, 100, "country.invalid");
        if (provinceOrState != null) {
            provinceOrState = provinceOrState.strip();
            if (provinceOrState.length() > 100) throw new WorkshopRuleException("province.invalid");
            if (provinceOrState.isEmpty()) provinceOrState = null;
        }
        if (encryptedAuthCode == null || !encryptedAuthCode.startsWith("$2"))
            throw new WorkshopRuleException("authCode.invalid");
    }

    @Override public String toString() {
        return "AuthorizedWorkshop[id=" + id + ", workshopId=" + workshopId.value() + "]";
    }
}
