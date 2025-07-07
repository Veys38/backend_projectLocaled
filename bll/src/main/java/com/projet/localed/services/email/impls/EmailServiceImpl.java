package com.projet.localed.services.email.impls;

import com.projet.localed.services.email.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void send(String to, String subject, String content) {
        System.out.println("=== EMAIL ENVOYÉ ===");
        System.out.println("À       : " + to);
        System.out.println("Sujet   : " + subject);
        System.out.println("Contenu : " + content);
        System.out.println("====================");
    }
}
