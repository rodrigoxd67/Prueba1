package com.upc.acme.workshops.application.commandservices;

import com.upc.acme.workshops.application.commands.CreateAuthorizedWorkshopCommand;
import com.upc.acme.workshops.domain.model.aggregates.AuthorizedWorkshop;

public interface AuthorizedWorkshopCommandService {
    AuthorizedWorkshop handle(CreateAuthorizedWorkshopCommand command);
}
