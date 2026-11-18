package com.upc.acme.workshops.infrastructure.persistence.jpa.repositories;

import com.upc.acme.workshops.infrastructure.persistence.jpa.entities.AuthorizedWorkshopPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizedWorkshopJpaRepository extends JpaRepository<AuthorizedWorkshopPersistenceEntity, Integer> {
    boolean existsByRegistrationKey(String registrationKey);
}
