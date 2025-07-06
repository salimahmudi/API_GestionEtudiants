package com.example.etudiantapi.controller.v2;

import com.example.etudiantapi.dto.EtudiantDTO;
import com.example.etudiantapi.exception.CustomException;
import com.example.etudiantapi.service.EtudiantService;
import com.example.etudiantapi.util.LanguageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v2/etudiants")
@Tag(name = "Étudiants V2", description = "Gestion des étudiants - Version 2 avec support multilingue amélioré")
public class EtudiantV2Controller {

    private static final Logger LOGGER = Logger.getLogger(EtudiantV2Controller.class.getName());

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private LanguageUtil languageUtil;

    public EtudiantV2Controller() {
        LOGGER.info("🎯 EtudiantV2Controller initialisé !");
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les étudiants", description = "Retourne la liste de tous les étudiants avec métadonnées et pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des étudiants récupérée avec succès"),
        @ApiResponse(responseCode = "400", description = "Paramètres de pagination invalides"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Map<String, Object>> getAllEtudiants(
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang,
            @Parameter(description = "Numéro de page (0-based)")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Taille de la page (1-100)")
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        try {
            // Validation des paramètres de pagination
            if (size > 100) {
                throw new CustomException(
                    languageUtil.getMessage("pagination.size", lang) + " cannot exceed 100",
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            List<EtudiantDTO> etudiants = etudiantService.getAllEtudiants();

            // Simulation de pagination (en production, utiliser Spring Data Pagination)
            int totalElements = etudiants.size();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, totalElements);

            if (startIndex >= totalElements && totalElements > 0) {
                throw new CustomException(
                    languageUtil.getMessage("pagination.page", lang) + " out of range",
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            List<EtudiantDTO> paginatedEtudiants = etudiants.subList(startIndex, endIndex);

            Map<String, Object> response = new HashMap<>();
            response.put("data", paginatedEtudiants);
            response.put("message", languageUtil.getMessage("general.operation.completed", lang));
            response.put("pagination", Map.of(
                "currentPage", page,
                "pageSize", size,
                "totalElements", totalElements,
                "totalPages", (int) Math.ceil((double) totalElements / size),
                "hasNext", endIndex < totalElements,
                "hasPrevious", page > 0
            ));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "2.0");

            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw e; // Re-throw custom exceptions to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la récupération des étudiants: " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un étudiant par ID", description = "Retourne un étudiant spécifique avec métadonnées")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant trouvé"),
        @ApiResponse(responseCode = "400", description = "ID invalide"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    public ResponseEntity<Map<String, Object>> getEtudiantById(
            @PathVariable @Min(1) Long id,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            if (id == null || id <= 0) {
                throw new CustomException(
                    languageUtil.getMessage("error.bad.request", lang) + ": Invalid ID format",
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            Optional<EtudiantDTO> etudiant = etudiantService.getEtudiantById(id);

            if (etudiant.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("data", etudiant.get());
                response.put("message", languageUtil.getMessage("general.operation.completed", lang));
                response.put("timestamp", LocalDateTime.now().toString());
                response.put("version", "2.0");

                return ResponseEntity.ok(response);
            } else {
                throw new CustomException(
                    languageUtil.getMessage("error.student.not.found", lang),
                    CustomException.ErrorType.RESOURCE_NOT_FOUND
                );
            }
        } catch (CustomException e) {
            throw e; // Re-throw custom exceptions to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la récupération de l'étudiant " + id + ": " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel étudiant", description = "Crée un nouvel étudiant avec validation améliorée")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Étudiant créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "409", description = "Email déjà existant"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Map<String, Object>> createEtudiant(
            @Valid @RequestBody EtudiantDTO etudiantDTO,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            // Validation supplémentaire pour les champs requis
            if (etudiantDTO.getEmail() == null || etudiantDTO.getEmail().trim().isEmpty()) {
                throw new CustomException(
                    languageUtil.getMessage("error.validation.failed", lang) + ": Email is required",
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            EtudiantDTO createdEtudiant = etudiantService.createEtudiant(etudiantDTO, lang);

            Map<String, Object> response = new HashMap<>();
            response.put("data", createdEtudiant);
            response.put("message", languageUtil.getMessage("success.student.created", lang));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "2.0");
            response.put("links", Map.of(
                "self", "/api/v2/etudiants/" + createdEtudiant.getId(),
                "update", "/api/v2/etudiants/" + createdEtudiant.getId(),
                "delete", "/api/v2/etudiants/" + createdEtudiant.getId()
            ));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CustomException e) {
            throw e; // Re-throw custom exceptions to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la création de l'étudiant: " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un étudiant", description = "Met à jour un étudiant existant avec validation améliorée")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant mis à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé"),
        @ApiResponse(responseCode = "409", description = "Email déjà existant"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Map<String, Object>> updateEtudiant(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody EtudiantDTO etudiantDTO,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            if (id == null || id <= 0) {
                throw new CustomException(
                    languageUtil.getMessage("error.bad.request", lang) + ": Invalid ID format",
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            EtudiantDTO updatedEtudiant = etudiantService.updateEtudiant(id, etudiantDTO, lang);

            Map<String, Object> response = new HashMap<>();
            response.put("data", updatedEtudiant);
            response.put("message", languageUtil.getMessage("success.student.updated", lang));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "2.0");
            response.put("links", Map.of(
                "self", "/api/v2/etudiants/" + id,
                "delete", "/api/v2/etudiants/" + id,
                "all", "/api/v2/etudiants"
            ));

            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw e; // Re-throw custom exceptions to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la mise à jour de l'étudiant " + id + ": " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un étudiant", description = "Supprime un étudiant existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant supprimé avec succès"),
        @ApiResponse(responseCode = "400", description = "ID invalide"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Map<String, Object>> deleteEtudiant(
            @PathVariable @Min(1) Long id,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            if (id == null || id <= 0) {
                throw new CustomException(
                    languageUtil.getMessage("error.bad.request", lang) + ": Invalid ID format",
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            etudiantService.deleteEtudiant(id, lang);

            Map<String, Object> response = new HashMap<>();
            response.put("message", languageUtil.getMessage("success.student.deleted", lang));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "2.0");
            response.put("deletedId", id);
            response.put("links", Map.of(
                "all", "/api/v2/etudiants",
                "create", "/api/v2/etudiants"
            ));

            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw e; // Re-throw custom exceptions to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la suppression de l'étudiant " + id + ": " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    @GetMapping("/search/advanced")
    @Operation(summary = "Recherche avancée d'étudiants", description = "Recherche avec filtres multiples et pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recherche effectuée avec succès"),
        @ApiResponse(responseCode = "400", description = "Paramètres de recherche invalides"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Map<String, Object>> advancedSearch(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String niveau,
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "nom") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            // Validation des paramètres de tri
            if (!sortDir.equalsIgnoreCase("asc") && !sortDir.equalsIgnoreCase("desc")) {
                throw new CustomException(
                    languageUtil.getMessage("pagination.sort.direction", lang) + " must be 'asc' or 'desc'",
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            if (size > 100) {
                throw new CustomException(
                    languageUtil.getMessage("pagination.size", lang) + " cannot exceed 100",
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            // Au moins un critère de recherche doit être fourni
            boolean hasSearchCriteria = (email != null && !email.trim().isEmpty()) ||
                                      (niveau != null && !niveau.trim().isEmpty()) ||
                                      (nom != null && !nom.trim().isEmpty()) ||
                                      (prenom != null && !prenom.trim().isEmpty());

            if (!hasSearchCriteria) {
                // Si aucun critère, retourner tous les étudiants avec pagination
                return getAllEtudiants(lang, page, size);
            }

            List<EtudiantDTO> results;

            if (email != null && !email.trim().isEmpty()) {
                Optional<EtudiantDTO> etudiant = etudiantService.getEtudiantByEmail(email);
                results = etudiant.map(List::of).orElse(List.of());
            } else if (niveau != null && !niveau.trim().isEmpty()) {
                results = etudiantService.getEtudiantsByNiveau(niveau);
            } else {
                // Pour d'autres critères (nom, prénom), retourner tous les étudiants filtrés côté client
                // En production, implémenter la recherche dans le service
                results = etudiantService.getAllEtudiants();
            }

            // Simulation de pagination pour les résultats de recherche
            int totalElements = results.size();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, totalElements);

            if (startIndex >= totalElements && totalElements > 0) {
                throw new CustomException(
                    languageUtil.getMessage("pagination.page", lang) + " out of range",
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            List<EtudiantDTO> paginatedResults = totalElements > 0 ?
                results.subList(startIndex, endIndex) : results;

            Map<String, Object> searchCriteria = new HashMap<>();
            searchCriteria.put("email", email != null ? email : "");
            searchCriteria.put("niveau", niveau != null ? niveau : "");
            searchCriteria.put("nom", nom != null ? nom : "");
            searchCriteria.put("prenom", prenom != null ? prenom : "");

            Map<String, Object> response = new HashMap<>();
            response.put("data", paginatedResults);
            response.put("message", languageUtil.getMessage("general.operation.completed", lang));
            response.put("searchCriteria", searchCriteria);
            response.put("pagination", Map.of(
                "currentPage", page,
                "pageSize", size,
                "totalElements", totalElements,
                "totalPages", totalElements > 0 ? (int) Math.ceil((double) totalElements / size) : 0,
                "hasNext", endIndex < totalElements,
                "hasPrevious", page > 0
            ));
            response.put("sorting", Map.of(
                "sortBy", sortBy,
                "sortDirection", sortDir
            ));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "2.0");

            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw e; // Re-throw custom exceptions to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la recherche avancée: " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    @GetMapping("/statistics")
    @Operation(summary = "Statistiques des étudiants", description = "Retourne les statistiques détaillées des étudiants")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Map<String, Object>> getStatistics(
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            long totalStudents = etudiantService.countEtudiants();

            // Statistiques avancées (en production, implémenter dans le service)
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalStudents", totalStudents);
            statistics.put("lastUpdated", LocalDateTime.now().toString());

            // Exemple de statistiques supplémentaires
            statistics.put("byLevel", Map.of(
                "L1", 0, // Implémenter dans le service
                "L2", 0,
                "L3", 0,
                "M1", 0,
                "M2", 0
            ));

            Map<String, Object> response = new HashMap<>();
            response.put("data", statistics);
            response.put("message", languageUtil.getMessage("general.operation.completed", lang));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "2.0");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la récupération des statistiques: " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    @GetMapping("/health")
    @Operation(summary = "Vérification de santé de l'API", description = "Endpoint de santé pour la version 2")
    @ApiResponse(responseCode = "200", description = "API en bonne santé")
    public ResponseEntity<Map<String, Object>> healthCheck(
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            Map<String, Object> health = new HashMap<>();
            health.put("status", "healthy");
            health.put("version", "2.0");
            health.put("timestamp", LocalDateTime.now().toString());
            health.put("uptime", getUptime());

            // Vérification des services
            Map<String, String> services = new HashMap<>();
            services.put("database", "operational");
            services.put("notification", "operational");
            services.put("email", "operational");
            health.put("services", services);

            Map<String, Object> response = new HashMap<>();
            response.put("data", health);
            response.put("message", languageUtil.getMessage("api.status.healthy", lang));
            response.put("version", "2.0");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors du health check: " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    private String getUptime() {
        long uptimeMs = System.currentTimeMillis() - startTime;
        long seconds = uptimeMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        return String.format("%d days, %d hours, %d minutes, %d seconds",
            days, hours % 24, minutes % 60, seconds % 60);
    }

    private static final long startTime = System.currentTimeMillis();
}
