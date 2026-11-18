package com.upc.acme.workshops.interfaces.resources;

public record AuthorizedWorkshopResource(Integer id, String workshopId,
        String managerFirstName, String managerLastName, String contactEmail,
        String contactPhone, String workshopLegalName, String city,
        String provinceOrState, String countryOrRegion) { }
