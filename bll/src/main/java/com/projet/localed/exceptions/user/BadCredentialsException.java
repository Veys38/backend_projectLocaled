package com.projet.localed.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadCredentialsException extends ResponseStatusException {
    public BadCredentialsException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
