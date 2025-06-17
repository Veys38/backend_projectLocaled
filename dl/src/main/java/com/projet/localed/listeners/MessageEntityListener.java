package com.projet.localed.listeners;

import com.projet.localed.entities.Message;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class MessageEntityListener {

    @PrePersist
    public void prePersist(Message message) {
        if (message.getSentAt() == null) {
            message.setSentAt(LocalDateTime.now());
        }
    }
}
