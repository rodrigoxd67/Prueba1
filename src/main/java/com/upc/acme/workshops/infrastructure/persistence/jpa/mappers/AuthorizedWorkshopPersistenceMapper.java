package com.upc.acme.workshops.infrastructure.persistence.jpa.mappers;

import com.upc.acme.workshops.domain.model.aggregates.AuthorizedWorkshop;
import com.upc.acme.workshops.domain.model.valueobjects.PersonName;
import com.upc.acme.workshops.infrastructure.persistence.jpa.entities.AuthorizedWorkshopPersistenceEntity;

public final class AuthorizedWorkshopPersistenceMapper {
    private AuthorizedWorkshopPersistenceMapper() { }

    public static AuthorizedWorkshopPersistenceEntity toEntity(AuthorizedWorkshop workshop) {
        var entity = new AuthorizedWorkshopPersistenceEntity();
        entity.setId(workshop.id());
        entity.setWorkshopId(workshop.workshopId());
        entity.setManagerFirstName(workshop.managerName().firstName());
        entity.setManagerLastName(workshop.managerName().lastName());
        entity.setContactPhone(workshop.contactPhone());
        entity.setContactEmail(workshop.contactEmail());
        entity.setWorkshopLegalName(workshop.workshopLegalName());
        entity.setCity(workshop.city());
        entity.setProvinceOrState(workshop.provinceOrState());
        entity.setCountryOrRegion(workshop.countryOrRegion());
        entity.setEncryptedAuthCode(workshop.encryptedAuthCode());
        entity.setCreatedAt(workshop.createdAt());
        entity.setUpdatedAt(workshop.updatedAt());
        entity.setRegistrationKey(WorkshopIdentityKey.from(workshop.managerName(), workshop.workshopLegalName(), workshop.countryOrRegion()));
        return entity;
    }

    public static AuthorizedWorkshop toDomain(AuthorizedWorkshopPersistenceEntity entity) {
        return new AuthorizedWorkshop(entity.getId(), entity.getWorkshopId(),
                new PersonName(entity.getManagerFirstName(), entity.getManagerLastName()),
                entity.getContactPhone(), entity.getContactEmail(), entity.getWorkshopLegalName(),
                entity.getCity(), entity.getProvinceOrState(), entity.getCountryOrRegion(),
                entity.getEncryptedAuthCode(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
