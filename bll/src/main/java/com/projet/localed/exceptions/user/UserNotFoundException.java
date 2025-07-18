package com.projet.localed.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
