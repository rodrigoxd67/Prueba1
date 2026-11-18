package com.upc.acme.workshops.interfaces.transforms;

import com.upc.acme.workshops.application.commands.CreateAuthorizedWorkshopCommand;
import com.upc.acme.workshops.domain.model.aggregates.AuthorizedWorkshop;
import com.upc.acme.workshops.interfaces.resources.*;

public final class AuthorizedWorkshopTransform {
    private AuthorizedWorkshopTransform() { }

    public static CreateAuthorizedWorkshopCommand toCommand(CreateAuthorizedWorkshopResource resource) {
        return new CreateAuthorizedWorkshopCommand(resource.managerFirstName(), resource.managerLastName(),
                resource.contactPhone(), resource.contactEmail(), resource.workshopLegalName(), resource.city(),
                resource.provinceOrState(), resource.countryOrRegion(), resource.maintenanceAuthCode());
    }

    public static AuthorizedWorkshopResource toResource(AuthorizedWorkshop workshop) {
        return new AuthorizedWorkshopResource(workshop.id(), workshop.workshopId().value().toString(),
                workshop.managerName().firstName(), workshop.managerName().lastName(),
                workshop.contactEmail().value(), workshop.contactPhone().value(), workshop.workshopLegalName(),
                workshop.city(), workshop.provinceOrState(), workshop.countryOrRegion());
    }
}
