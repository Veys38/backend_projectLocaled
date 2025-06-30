package com.projet.localed.exceptions.ad;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AdNotFoundException extends ResponseStatusException {
    public AdNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Annonce introuvable");
    }
}
