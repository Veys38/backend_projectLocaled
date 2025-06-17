package com.projet.localed.listeners;

import com.projet.localed.entities.Image;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class ImageEntityListener {

    @PrePersist
    @PreUpdate
    public void validate(Image image) {
        boolean hasUser = image.getUser() != null;
        boolean hasAd = image.getAd() != null;

        if (hasUser == hasAd) {
            throw new IllegalStateException("L'image doit être liée soit à un utilisateur, soit à une annonce (mais pas les deux).");
        }
    }
}
