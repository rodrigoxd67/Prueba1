package com.upc.acme.workshops.domain.exceptions;

public class WorkshopRuleException extends RuntimeException {
    public WorkshopRuleException(String key) { super(key); }
}
