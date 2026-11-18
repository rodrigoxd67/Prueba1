package com.upc.acme.workshops.interfaces.controllers;

import com.upc.acme.workshops.application.commandservices.AuthorizedWorkshopCommandService;
import com.upc.acme.workshops.interfaces.resources.*;
import com.upc.acme.workshops.interfaces.transforms.AuthorizedWorkshopTransform;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/workshops")
public class AuthorizedWorkshopController {
    private final AuthorizedWorkshopCommandService service;

    public AuthorizedWorkshopController(AuthorizedWorkshopCommandService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<AuthorizedWorkshopResource> create(@Valid @RequestBody CreateAuthorizedWorkshopResource resource, Locale locale) {
        var workshop = service.handle(AuthorizedWorkshopTransform.toCommand(resource));
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_LANGUAGE, locale.toLanguageTag())
                .body(AuthorizedWorkshopTransform.toResource(workshop));
    }
}
