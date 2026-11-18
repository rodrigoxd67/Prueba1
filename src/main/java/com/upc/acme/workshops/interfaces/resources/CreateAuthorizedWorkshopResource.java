package com.upc.acme.workshops.interfaces.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAuthorizedWorkshopResource(
        @NotBlank(message = "{manager.firstName.invalid}") @Size(max = 100, message = "{manager.firstName.invalid}") String managerFirstName,
        @NotBlank(message = "{manager.lastName.invalid}") @Size(max = 100, message = "{manager.lastName.invalid}") String managerLastName,
        @NotBlank(message = "{phone.invalid}") @Size(max = 16, message = "{phone.invalid}") String contactPhone,
        @NotBlank(message = "{email.invalid}") @Size(max = 254, message = "{email.invalid}") String contactEmail,
        @NotBlank(message = "{legalName.invalid}") @Size(max = 200, message = "{legalName.invalid}") String workshopLegalName,
        @NotBlank(message = "{city.invalid}") @Size(max = 100, message = "{city.invalid}") String city,
        @Size(max = 100, message = "{province.invalid}") String provinceOrState,
        @NotBlank(message = "{country.invalid}") @Size(max = 100, message = "{country.invalid}") String countryOrRegion,
        @NotBlank(message = "{authCode.invalid}") @Size(min = 8, max = 8, message = "{authCode.invalid}") String maintenanceAuthCode) {
    @Override public String toString() { return "CreateAuthorizedWorkshopResource[redacted]"; }
}
