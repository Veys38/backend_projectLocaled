// chemin : com.projet.localed.exceptions.user.EmailAlreadyVerifiedException

package com.projet.localed.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyVerifiedException extends RuntimeException {
    public EmailAlreadyVerifiedException() {
        super("Compte déjà activé");
    }
}
