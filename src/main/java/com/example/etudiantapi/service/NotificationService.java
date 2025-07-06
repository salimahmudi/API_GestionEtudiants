package com.example.etudiantapi.service;

import com.example.etudiantapi.entity.Etudiant;
import com.example.etudiantapi.util.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NotificationService {

    private static final Logger LOGGER = Logger.getLogger(NotificationService.class.getName());

    @Autowired
    private LanguageUtil languageUtil;

    @Value("${notification.service.base-url}")
    private String notificationServiceBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public enum NotificationType {
        CREATION, MODIFICATION, SUPPRESSION
    }

    public enum NotificationChannel {
        EMAIL, SMS, PUSH
    }

    public CompletableFuture<Boolean> sendNotificationAsync(Etudiant etudiant, NotificationType type, String language) {
        LOGGER.info("===== DEMARRAGE NOTIFICATIONS ASYNCHRONES =====");
        LOGGER.info("Etudiant: " + etudiant.getPrenom() + " " + etudiant.getNom());
        LOGGER.info("Email: " + etudiant.getEmail());
        LOGGER.info("Type: " + type);

        return CompletableFuture.supplyAsync(() -> {
            try {
                // Envoi sur tous les canaux disponibles
                boolean emailSent = sendNotification(etudiant, type, NotificationChannel.EMAIL, language);
                boolean smsSent = sendNotification(etudiant, type, NotificationChannel.SMS, language);
                boolean pushSent = sendNotification(etudiant, type, NotificationChannel.PUSH, language);

                LOGGER.info("===== RESULTATS NOTIFICATIONS =====");
                LOGGER.info("EMAIL: " + (emailSent ? "ENVOYE AVEC SUCCES" : "ECHEC"));
                LOGGER.info("SMS: " + (smsSent ? "ENVOYE AVEC SUCCES" : "ECHEC"));
                LOGGER.info("PUSH: " + (pushSent ? "ENVOYE AVEC SUCCES" : "ECHEC"));
                LOGGER.info("=====================================");

                return emailSent || smsSent || pushSent;
            } catch (Exception e) {
                LOGGER.severe("ERREUR CRITIQUE dans sendNotificationAsync: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        });
    }

    public boolean sendNotification(Etudiant etudiant, NotificationType type, NotificationChannel channel, String language) {
        try {
            String message = buildMessage(etudiant, type, language);
            String subject = buildSubject(type, language);

            switch (channel) {
                case EMAIL:
                    return sendEmail(etudiant.getEmail(), subject, message);
                case SMS:
                    return sendSMS(etudiant.getTelephone(), message);
                case PUSH:
                    return sendPushNotification(etudiant.getId().toString(), subject, message);
                default:
                    LOGGER.warning("Canal de notification non supporte: " + channel);
                    return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'envoi de notification", e);
            return false;
        }
    }

    private boolean sendEmail(String email, String subject, String message) {
        try {
            String url = notificationServiceBaseUrl + "/api/v2/notifications/email";
            
            Map<String, String> emailRequest = new HashMap<>();
            emailRequest.put("to", email);
            emailRequest.put("subject", subject);
            emailRequest.put("text", message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(emailRequest, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();
                boolean success = (Boolean) responseBody.get("success");
                String responseMessage = (String) responseBody.get("message");
                
                LOGGER.info("===== EMAIL RESPONSE =====");
                LOGGER.info("Success: " + success);
                LOGGER.info("Message: " + responseMessage);
                LOGGER.info("============================");
                
                return success;
            } else {
                LOGGER.warning("Email service responded with error: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'envoi d'email via API externe", e);
            return false;
        }
    }

    private boolean sendSMS(String telephone, String message) {
        if (telephone == null || telephone.trim().isEmpty()) {
            LOGGER.warning("Numero de telephone non fourni pour l'envoi de SMS");
            return false;
        }

        try {
            String url = notificationServiceBaseUrl + "/api/v2/notifications/sms";
            
            Map<String, String> smsRequest = new HashMap<>();
            smsRequest.put("phoneNumber", telephone);
            smsRequest.put("message", message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(smsRequest, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();
                boolean success = (Boolean) responseBody.get("success");
                String responseMessage = (String) responseBody.get("message");
                
                LOGGER.info("===== SMS RESPONSE =====");
                LOGGER.info("Success: " + success);
                LOGGER.info("Message: " + responseMessage);
                LOGGER.info("=========================");
                
                return success;
            } else {
                LOGGER.warning("SMS service responded with error: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'envoi de SMS via API externe", e);
            return false;
        }
    }

    private boolean sendPushNotification(String userId, String title, String message) {
        try {
            String url = notificationServiceBaseUrl + "/api/v2/notifications/push";
            
            Map<String, String> pushRequest = new HashMap<>();
            pushRequest.put("userId", userId);
            pushRequest.put("title", title);
            pushRequest.put("message", message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(pushRequest, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();
                boolean success = (Boolean) responseBody.get("success");
                String responseMessage = (String) responseBody.get("message");
                
                LOGGER.info("===== PUSH NOTIFICATION RESPONSE =====");
                LOGGER.info("Success: " + success);
                LOGGER.info("Message: " + responseMessage);
                LOGGER.info("=====================================");
                
                return success;
            } else {
                LOGGER.warning("Push notification service responded with error: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'envoi de notification push via API externe", e);
            return false;
        }
    }

    private String buildSubject(NotificationType type, String language) {
        switch (type) {
            case CREATION:
                return "Bienvenue - Votre dossier etudiant a ete cree";
            case MODIFICATION:
                return "Mise a jour - Votre dossier etudiant";
            case SUPPRESSION:
                return "Suppression - Votre dossier etudiant";
            default:
                return "Notification - Dossier etudiant";
        }
    }

    private String buildMessage(Etudiant etudiant, NotificationType type, String language) {
        String messageTemplate;

        switch (type) {
            case CREATION:
                messageTemplate = "Bonjour %s %s,\n\nVotre dossier etudiant a ete cree avec succes.\n\nInformations:\n- Email: %s\n- Niveau: %s\n- Telephone: %s\n\nCordialement,\nL'equipe de gestion des etudiants";
                return String.format(messageTemplate, etudiant.getPrenom(), etudiant.getNom(), etudiant.getEmail(), etudiant.getNiveau(), etudiant.getTelephone());
            case MODIFICATION:
                messageTemplate = "Bonjour %s %s,\n\nVotre dossier etudiant a ete mis a jour.\n\nInformations actuelles:\n- Email: %s\n- Niveau: %s\n- Telephone: %s\n\nCordialement,\nL'equipe de gestion des etudiants";
                return String.format(messageTemplate, etudiant.getPrenom(), etudiant.getNom(), etudiant.getEmail(), etudiant.getNiveau(), etudiant.getTelephone());
            case SUPPRESSION:
                messageTemplate = "Bonjour %s %s,\n\nVotre dossier etudiant a ete supprime.\n\nSi vous pensez qu'il s'agit d'une erreur, contactez-nous.\n\nCordialement,\nL'equipe de gestion des etudiants";
                return String.format(messageTemplate, etudiant.getPrenom(), etudiant.getNom());
            default:
                messageTemplate = "Bonjour %s %s,\n\nNotification concernant votre dossier etudiant.\n\nContact: %s\n\nCordialement,\nL'equipe de gestion des etudiants";
                return String.format(messageTemplate, etudiant.getPrenom(), etudiant.getNom(), etudiant.getEmail());
        }
    }
}
