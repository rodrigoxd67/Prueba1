package com.upc.acme.workshops.infrastructure.persistence.jpa.entities;

import com.upc.acme.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.upc.acme.workshops.domain.model.valueobjects.*;
import com.upc.acme.workshops.infrastructure.persistence.jpa.converters.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authorized_workshops", uniqueConstraints = {
    @UniqueConstraint(name = "uk_workshop_identity", columnNames = "registration_key"),
    @UniqueConstraint(name = "uk_workshop_business_id", columnNames = "workshop_id")
})
@Getter
@Setter
@NoArgsConstructor
public class AuthorizedWorkshopPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Convert(converter = DaimlerWorkshopIdPersistenceConverter.class)
    @Column(name = "workshop_id", nullable = false, updatable = false, length = 36)
    private DaimlerWorkshopId workshopId;
    @Column(name = "manager_first_name", nullable = false, length = 100)
    private String managerFirstName;
    @Column(name = "manager_last_name", nullable = false, length = 100)
    private String managerLastName;
    @Convert(converter = PhoneNumberPersistenceConverter.class)
    @Column(name = "contact_phone", nullable = false, length = 16)
    private PhoneNumber contactPhone;
    @Convert(converter = EmailAddressPersistenceConverter.class)
    @Column(name = "contact_email", nullable = false, length = 254)
    private EmailAddress contactEmail;
    @Column(name = "workshop_legal_name", nullable = false, length = 200)
    private String workshopLegalName;
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    @Column(name = "province_or_state", length = 100)
    private String provinceOrState;
    @Column(name = "country_or_region", nullable = false, length = 100)
    private String countryOrRegion;
    @Column(name = "encrypted_auth_code", nullable = false, length = 60)
    private String encryptedAuthCode;
    @Column(name = "registration_key", nullable = false, length = 64)
    private String registrationKey;
}
