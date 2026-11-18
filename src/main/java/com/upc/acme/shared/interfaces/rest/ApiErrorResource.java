package com.upc.acme.shared.interfaces.rest;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResource(Instant timestamp, int status, String message,
                               String path, Map<String, String> errors) { }
