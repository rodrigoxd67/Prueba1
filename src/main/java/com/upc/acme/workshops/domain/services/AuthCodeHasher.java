package com.upc.acme.workshops.domain.services;

public interface AuthCodeHasher {
    String hash(String rawCode);
}
