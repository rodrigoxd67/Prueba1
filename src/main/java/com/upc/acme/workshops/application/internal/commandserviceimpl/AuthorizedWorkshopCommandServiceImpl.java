package com.upc.acme.workshops.application.internal.commandserviceimpl;

import com.upc.acme.workshops.application.commands.CreateAuthorizedWorkshopCommand;
import com.upc.acme.workshops.application.commandservices.AuthorizedWorkshopCommandService;
import com.upc.acme.workshops.domain.exceptions.*;
import com.upc.acme.workshops.domain.model.aggregates.AuthorizedWorkshop;
import com.upc.acme.workshops.domain.model.valueobjects.*;
import com.upc.acme.workshops.domain.repositories.AuthorizedWorkshopRepository;
import com.upc.acme.workshops.domain.services.AuthCodeHasher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorizedWorkshopCommandServiceImpl implements AuthorizedWorkshopCommandService {
    private final AuthorizedWorkshopRepository repository;
    private final AuthCodeHasher hasher;

    public AuthorizedWorkshopCommandServiceImpl(AuthorizedWorkshopRepository repository, AuthCodeHasher hasher) {
        this.repository = repository;
        this.hasher = hasher;
    }

    @Override
    @Transactional
    public AuthorizedWorkshop handle(CreateAuthorizedWorkshopCommand command) {
        if (command == null) throw new WorkshopRuleException("request.invalid");
        String code = command.maintenanceAuthCode();
        if (code == null || code.isBlank() || code.length() != 8)
            throw new WorkshopRuleException("authCode.invalid");
        var workshop = new AuthorizedWorkshop(null, DaimlerWorkshopId.generate(),
                new PersonName(command.managerFirstName(), command.managerLastName()),
                new PhoneNumber(command.contactPhone()), new EmailAddress(command.contactEmail()),
                command.workshopLegalName(), command.city(), command.provinceOrState(),
                command.countryOrRegion(), hasher.hash(code), null, null);
        if (repository.existsByIdentity(workshop.managerName(), workshop.workshopLegalName(), workshop.countryOrRegion()))
            throw new DuplicateWorkshopException();
        return repository.save(workshop);
    }
}
