package com.upc.acme.workshops.infrastructure.persistence.jpa.adapters;

import com.upc.acme.workshops.domain.exceptions.DuplicateWorkshopException;
import com.upc.acme.workshops.domain.model.aggregates.AuthorizedWorkshop;
import com.upc.acme.workshops.domain.model.valueobjects.PersonName;
import com.upc.acme.workshops.domain.repositories.AuthorizedWorkshopRepository;
import com.upc.acme.workshops.infrastructure.persistence.jpa.mappers.*;
import com.upc.acme.workshops.infrastructure.persistence.jpa.repositories.AuthorizedWorkshopJpaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import java.util.Locale;

@Repository
public class AuthorizedWorkshopPersistenceAdapter implements AuthorizedWorkshopRepository {
    private final AuthorizedWorkshopJpaRepository repository;

    public AuthorizedWorkshopPersistenceAdapter(AuthorizedWorkshopJpaRepository repository) {
        this.repository = repository;
    }

    @Override public boolean existsByIdentity(PersonName name, String legalName, String country) {
        return repository.existsByRegistrationKey(WorkshopIdentityKey.from(name, legalName, country));
    }

    @Override public AuthorizedWorkshop save(AuthorizedWorkshop workshop) {
        try {
            return AuthorizedWorkshopPersistenceMapper.toDomain(repository.saveAndFlush(
                    AuthorizedWorkshopPersistenceMapper.toEntity(workshop)));
        } catch (DataIntegrityViolationException exception) {
            // The unique constraint also protects concurrent registrations.
            if (exception.getMostSpecificCause().getMessage().toLowerCase(Locale.ROOT).contains("uk_workshop_identity"))
                throw new DuplicateWorkshopException();
            throw exception;
        }
    }
}
