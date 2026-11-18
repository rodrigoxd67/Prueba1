package com.upc.acme.workshops.domain.exceptions;

public class DuplicateWorkshopException extends RuntimeException {
    public DuplicateWorkshopException() { super("workshop.duplicate"); }
}
