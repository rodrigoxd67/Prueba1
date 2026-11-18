package com.upc.acme.workshops.infrastructure.hashing;

import com.upc.acme.workshops.domain.services.AuthCodeHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptAuthCodeHasher implements AuthCodeHasher {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override public String hash(String rawCode) { return encoder.encode(rawCode); }
}
