package com.upc.acme.workshops.domain.model.valueobjects;

import com.upc.acme.workshops.domain.exceptions.WorkshopRuleException;
import java.util.UUID;

public record DaimlerWorkshopId(UUID value) {
    public DaimlerWorkshopId {
        if (value == null || value.equals(new UUID(0, 0)))
            throw new WorkshopRuleException("workshopId.invalid");
    }

    public static DaimlerWorkshopId generate() { return new DaimlerWorkshopId(UUID.randomUUID()); }
}
