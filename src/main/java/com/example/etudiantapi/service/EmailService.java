package com.example.etudiantapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String message) {
        try {
            LOGGER.info("===== ENVOI EMAIL REEL =====");
            LOGGER.info("Destinataire: " + to);
            LOGGER.info("Sujet: " + subject);
            LOGGER.info("Message: " + message);

            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom("noreply@etudiantapi.com");
            email.setTo(to);
            email.setSubject(subject);
            email.setText(message);

            mailSender.send(email);

            LOGGER.info("EMAIL REEL ENVOYE AVEC SUCCES !");
            LOGGER.info("============================");
            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERREUR lors de l'envoi d'email reel: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean isEmailConfigured() {
        try {
            // Test simple pour vérifier si le service email est configuré
            return mailSender != null;
        } catch (Exception e) {
            return false;
        }
    }
}
