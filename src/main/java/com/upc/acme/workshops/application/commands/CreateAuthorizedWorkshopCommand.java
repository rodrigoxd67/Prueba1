package com.upc.acme.workshops.application.commands;

public record CreateAuthorizedWorkshopCommand(String managerFirstName, String managerLastName,
        String contactPhone, String contactEmail, String workshopLegalName, String city,
        String provinceOrState, String countryOrRegion, String maintenanceAuthCode) {
    @Override public String toString() { return "CreateAuthorizedWorkshopCommand[redacted]"; }
}
