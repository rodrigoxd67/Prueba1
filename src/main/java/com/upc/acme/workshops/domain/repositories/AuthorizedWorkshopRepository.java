package com.upc.acme.workshops.domain.repositories;

import com.upc.acme.workshops.domain.model.aggregates.AuthorizedWorkshop;
import com.upc.acme.workshops.domain.model.valueobjects.PersonName;

public interface AuthorizedWorkshopRepository {
    boolean existsByIdentity(PersonName name, String legalName, String country);
    AuthorizedWorkshop save(AuthorizedWorkshop workshop);
}
