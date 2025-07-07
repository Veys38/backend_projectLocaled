package com.projet.localed.services.email;

public interface EmailService {
    void send(String to, String subject, String content);
}
