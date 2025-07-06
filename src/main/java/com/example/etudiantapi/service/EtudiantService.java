package com.example.etudiantapi.service;

import com.example.etudiantapi.dto.EtudiantDTO;
import com.example.etudiantapi.entity.Etudiant;
import com.example.etudiantapi.exception.CustomException;
import com.example.etudiantapi.repository.EtudiantRepository;
import com.example.etudiantapi.util.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class EtudiantService {

    private static final Logger LOGGER = Logger.getLogger(EtudiantService.class.getName());

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LanguageUtil languageUtil;

    public List<EtudiantDTO> getAllEtudiants() {
        return etudiantRepository.findAll()
                .stream()
                .map(EtudiantDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<EtudiantDTO> getEtudiantById(Long id) {
        return etudiantRepository.findById(id)
                .map(EtudiantDTO::new);
    }

    public Optional<EtudiantDTO> getEtudiantByEmail(String email) {
        return etudiantRepository.findByEmail(email)
                .map(EtudiantDTO::new);
    }

    public List<EtudiantDTO> getEtudiantsByNiveau(String niveau) {
        return etudiantRepository.findByNiveau(niveau)
                .stream()
                .map(EtudiantDTO::new)
                .collect(Collectors.toList());
    }

    public EtudiantDTO createEtudiant(EtudiantDTO etudiantDTO, String language) {
        LOGGER.info("🚀 Création d'un étudiant: " + etudiantDTO.getEmail());

        // Vérification de l'unicité de l'email
        if (etudiantRepository.existsByEmail(etudiantDTO.getEmail())) {
            String message = languageUtil.getMessage("error.email.already.exists", language);
            throw new CustomException(message, CustomException.ErrorType.BUSINESS_RULE_VIOLATION);
        }

        Etudiant etudiant = etudiantDTO.toEntity();
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        LOGGER.info("✅ Étudiant sauvegardé avec ID: " + savedEtudiant.getId());

        // Envoi de notification asynchrone
        LOGGER.info("📧 Démarrage de l'envoi de notifications...");
        sendNotificationAsync(savedEtudiant, NotificationService.NotificationType.CREATION, language);

        LOGGER.info("Étudiant créé avec succès: " + savedEtudiant.getId());
        return new EtudiantDTO(savedEtudiant);
    }

    public EtudiantDTO updateEtudiant(Long id, EtudiantDTO etudiantDTO, String language) {
        Optional<Etudiant> existingEtudiant = etudiantRepository.findById(id);

        if (!existingEtudiant.isPresent()) {
            String message = languageUtil.getMessage("error.student.not.found", language);
            throw new CustomException(message, CustomException.ErrorType.RESOURCE_NOT_FOUND);
        }

        // Vérification de l'unicité de l'email (si changé)
        if (!existingEtudiant.get().getEmail().equals(etudiantDTO.getEmail()) &&
                etudiantRepository.existsByEmail(etudiantDTO.getEmail())) {
            String message = languageUtil.getMessage("error.email.already.exists", language);
            throw new CustomException(message, CustomException.ErrorType.BUSINESS_RULE_VIOLATION);
        }

        Etudiant etudiant = etudiantDTO.toEntity();
        etudiant.setId(id);
        etudiant.setDateCreation(existingEtudiant.get().getDateCreation());

        Etudiant updatedEtudiant = etudiantRepository.save(etudiant);

        // Envoi de notification asynchrone
        sendNotificationAsync(updatedEtudiant, NotificationService.NotificationType.MODIFICATION, language);

        LOGGER.info("Étudiant mis à jour avec succès: " + updatedEtudiant.getId());
        return new EtudiantDTO(updatedEtudiant);
    }

    public void deleteEtudiant(Long id, String language) {
        Optional<Etudiant> etudiant = etudiantRepository.findById(id);

        if (!etudiant.isPresent()) {
            String message = languageUtil.getMessage("error.student.not.found", language);
            throw new CustomException(message, CustomException.ErrorType.RESOURCE_NOT_FOUND);
        }

        // Envoi de notification avant suppression
        sendNotificationAsync(etudiant.get(), NotificationService.NotificationType.SUPPRESSION, language);

        etudiantRepository.deleteById(id);
        LOGGER.info("Étudiant supprimé avec succès: " + id);
    }

    public long countEtudiants() {
        return etudiantRepository.count();
    }

    private void sendNotificationAsync(Etudiant etudiant, NotificationService.NotificationType type, String language) {
        CompletableFuture<Boolean> notificationFuture = notificationService.sendNotificationAsync(etudiant, type, language);

        notificationFuture.whenComplete((success, throwable) -> {
            if (throwable != null) {
                LOGGER.log(Level.WARNING, "Échec de l'envoi de notification pour l'étudiant " + etudiant.getId(), throwable);
            } else if (!success) {
                LOGGER.warning("Notification non envoyée pour l'étudiant " + etudiant.getId());
            } else {
                LOGGER.info("Notification envoyée avec succès pour l'étudiant " + etudiant.getId());
            }
        });
    }
}
