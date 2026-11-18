package com.upc.acme.workshops.infrastructure.persistence.jpa.mappers;

import com.upc.acme.workshops.domain.model.valueobjects.PersonName;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.HexFormat;
import java.util.Locale;

public final class WorkshopIdentityKey {
    private WorkshopIdentityKey() { }

    public static String from(PersonName name, String legalName, String country) {
        StringBuilder key = new StringBuilder();
        for (String part : new String[]{name.firstName(), name.lastName(), legalName, country}) {
            String normalized = Normalizer.normalize(part, Normalizer.Form.NFKC)
                    .strip().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
            key.append(normalized.length()).append(':').append(normalized);
        }
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(key.toString().getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 unavailable", exception);
        }
    }
}
