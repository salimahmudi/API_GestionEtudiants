package com.example.etudiantapi.controller;

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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/etudiants")
@Tag(name = "Étudiants V1 (Deprecated)", description = "⚠️ Version 1 dépréciée - Utilisez la version 2 (/api/v2/etudiants)")
@Deprecated
public class EtudiantController {

    private static final Logger LOGGER = Logger.getLogger(EtudiantController.class.getName());

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private LanguageUtil languageUtil;

    public EtudiantController() {
        LOGGER.info("🎯 EtudiantController initialisé !");
        LOGGER.warning("⚠️ Cette version de l'API est dépréciée. Utilisez /api/v2/etudiants");
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les étudiants", description = "⚠️ DEPRECATED - Utilisez /api/v2/etudiants")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des étudiants récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @Deprecated
    public ResponseEntity<Map<String, Object>> getAllEtudiants(
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            LOGGER.warning("⚠️ Endpoint déprécié utilisé: GET /api/v1/etudiants");
            List<EtudiantDTO> etudiants = etudiantService.getAllEtudiants();

            Map<String, Object> response = new HashMap<>();
            response.put("data", etudiants);
            response.put("message", languageUtil.getMessage("general.operation.completed", lang));
            response.put("total", etudiants.size());
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "1.0");
            response.put("deprecated", true);
            response.put("deprecationMessage", languageUtil.getMessage("api.deprecated.message", lang));
            response.put("migrateToVersion", "/api/v2/etudiants");

            return ResponseEntity.ok()
                    .header("X-API-Deprecated", "true")
                    .header("X-API-Deprecated-Message", languageUtil.getMessage("api.deprecated.message", lang))
                    .header("X-API-New-Version", "/api/v2/etudiants")
                    .body(response);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la récupération des étudiants: " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un étudiant par ID", description = "⚠️ DEPRECATED - Utilisez /api/v2/etudiants/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant trouvé"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé"),
        @ApiResponse(responseCode = "400", description = "ID invalide")
    })
    @Deprecated
    public ResponseEntity<Map<String, Object>> getEtudiantById(
            @PathVariable Long id,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            if (id == null || id <= 0) {
                throw new CustomException(
                    languageUtil.getMessage("error.bad.request", lang),
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            LOGGER.warning("⚠️ Endpoint déprécié utilisé: GET /api/v1/etudiants/" + id);
            Optional<EtudiantDTO> etudiant = etudiantService.getEtudiantById(id);

            if (etudiant.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("data", etudiant.get());
                response.put("message", languageUtil.getMessage("general.operation.completed", lang));
                response.put("timestamp", LocalDateTime.now().toString());
                response.put("version", "1.0");
                response.put("deprecated", true);
                response.put("deprecationMessage", languageUtil.getMessage("api.deprecated.message", lang));
                response.put("migrateToVersion", "/api/v2/etudiants/" + id);

                return ResponseEntity.ok()
                        .header("X-API-Deprecated", "true")
                        .header("X-API-Deprecated-Message", languageUtil.getMessage("api.deprecated.message", lang))
                        .header("X-API-New-Version", "/api/v2/etudiants/" + id)
                        .body(response);
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
    @Operation(summary = "Créer un nouvel étudiant", description = "⚠️ DEPRECATED - Utilisez /api/v2/etudiants")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Étudiant créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "409", description = "Email déjà existant")
    })
    @Deprecated
    public ResponseEntity<Map<String, Object>> createEtudiant(
            @Valid @RequestBody EtudiantDTO etudiantDTO,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            LOGGER.warning("⚠️ Endpoint déprécié utilisé: POST /api/v1/etudiants");
            EtudiantDTO createdEtudiant = etudiantService.createEtudiant(etudiantDTO, lang);

            Map<String, Object> response = new HashMap<>();
            response.put("data", createdEtudiant);
            response.put("message", languageUtil.getMessage("success.student.created", lang));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "1.0");
            response.put("deprecated", true);
            response.put("deprecationMessage", languageUtil.getMessage("api.deprecated.message", lang));
            response.put("migrateToVersion", "/api/v2/etudiants");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("X-API-Deprecated", "true")
                    .header("X-API-Deprecated-Message", languageUtil.getMessage("api.deprecated.message", lang))
                    .header("X-API-New-Version", "/api/v2/etudiants")
                    .body(response);
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
    @Operation(summary = "Mettre à jour un étudiant", description = "⚠️ DEPRECATED - Utilisez /api/v2/etudiants/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "409", description = "Email déjà existant")
    })
    @Deprecated
    public ResponseEntity<Map<String, Object>> updateEtudiant(
            @PathVariable Long id,
            @Valid @RequestBody EtudiantDTO etudiantDTO,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            if (id == null || id <= 0) {
                throw new CustomException(
                    languageUtil.getMessage("error.bad.request", lang),
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            LOGGER.warning("⚠️ Endpoint déprécié utilisé: PUT /api/v1/etudiants/" + id);
            EtudiantDTO updatedEtudiant = etudiantService.updateEtudiant(id, etudiantDTO, lang);

            Map<String, Object> response = new HashMap<>();
            response.put("data", updatedEtudiant);
            response.put("message", languageUtil.getMessage("success.student.updated", lang));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "1.0");
            response.put("deprecated", true);
            response.put("deprecationMessage", languageUtil.getMessage("api.deprecated.message", lang));
            response.put("migrateToVersion", "/api/v2/etudiants/" + id);

            return ResponseEntity.ok()
                    .header("X-API-Deprecated", "true")
                    .header("X-API-Deprecated-Message", languageUtil.getMessage("api.deprecated.message", lang))
                    .header("X-API-New-Version", "/api/v2/etudiants/" + id)
                    .body(response);
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
    @Operation(summary = "Supprimer un étudiant", description = "⚠️ DEPRECATED - Utilisez /api/v2/etudiants/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé"),
        @ApiResponse(responseCode = "400", description = "ID invalide")
    })
    @Deprecated
    public ResponseEntity<Map<String, Object>> deleteEtudiant(
            @PathVariable Long id,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            if (id == null || id <= 0) {
                throw new CustomException(
                    languageUtil.getMessage("error.bad.request", lang),
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            LOGGER.warning("⚠️ Endpoint déprécié utilisé: DELETE /api/v1/etudiants/" + id);
            etudiantService.deleteEtudiant(id, lang);

            Map<String, Object> response = new HashMap<>();
            response.put("message", languageUtil.getMessage("success.student.deleted", lang));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "1.0");
            response.put("deprecated", true);
            response.put("deprecationMessage", languageUtil.getMessage("api.deprecated.message", lang));
            response.put("migrateToVersion", "/api/v2/etudiants/" + id);

            return ResponseEntity.ok()
                    .header("X-API-Deprecated", "true")
                    .header("X-API-Deprecated-Message", languageUtil.getMessage("api.deprecated.message", lang))
                    .header("X-API-New-Version", "/api/v2/etudiants/" + id)
                    .body(response);
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

    @GetMapping("/search")
    @Operation(summary = "Rechercher des étudiants", description = "⚠️ DEPRECATED - Utilisez /api/v2/etudiants/search/advanced")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recherche effectuée avec succès"),
        @ApiResponse(responseCode = "400", description = "Paramètres de recherche invalides")
    })
    @Deprecated
    public ResponseEntity<Map<String, Object>> searchEtudiants(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String niveau,
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            LOGGER.warning("⚠️ Endpoint déprécié utilisé: GET /api/v1/etudiants/search");

            if ((email == null || email.trim().isEmpty()) &&
                (niveau == null || niveau.trim().isEmpty())) {
                throw new CustomException(
                    languageUtil.getMessage("search.invalid.criteria", lang),
                    CustomException.ErrorType.VALIDATION_ERROR
                );
            }

            List<EtudiantDTO> results;
            if (email != null && !email.trim().isEmpty()) {
                Optional<EtudiantDTO> etudiant = etudiantService.getEtudiantByEmail(email);
                results = etudiant.map(List::of).orElse(List.of());
            } else {
                results = etudiantService.getEtudiantsByNiveau(niveau);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", results);
            response.put("message", languageUtil.getMessage("general.operation.completed", lang));
            response.put("total", results.size());
            response.put("searchCriteria", Map.of("email", email != null ? email : "", "niveau", niveau != null ? niveau : ""));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "1.0");
            response.put("deprecated", true);
            response.put("deprecationMessage", languageUtil.getMessage("api.deprecated.message", lang));
            response.put("migrateToVersion", "/api/v2/etudiants/search/advanced");

            return ResponseEntity.ok()
                    .header("X-API-Deprecated", "true")
                    .header("X-API-Deprecated-Message", languageUtil.getMessage("api.deprecated.message", lang))
                    .header("X-API-New-Version", "/api/v2/etudiants/search/advanced")
                    .body(response);
        } catch (CustomException e) {
            throw e; // Re-throw custom exceptions to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la recherche d'étudiants: " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Compter les étudiants", description = "⚠️ DEPRECATED - Utilisez /api/v2/etudiants/statistics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nombre d'étudiants récupéré avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @Deprecated
    public ResponseEntity<Map<String, Object>> countEtudiants(
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        try {
            LOGGER.warning("⚠️ Endpoint déprécié utilisé: GET /api/v1/etudiants/count");
            long count = etudiantService.countEtudiants();

            Map<String, Object> response = new HashMap<>();
            response.put("data", Map.of("count", count));
            response.put("message", languageUtil.getMessage("general.operation.completed", lang));
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("version", "1.0");
            response.put("deprecated", true);
            response.put("deprecationMessage", languageUtil.getMessage("api.deprecated.message", lang));
            response.put("migrateToVersion", "/api/v2/etudiants/statistics");

            return ResponseEntity.ok()
                    .header("X-API-Deprecated", "true")
                    .header("X-API-Deprecated-Message", languageUtil.getMessage("api.deprecated.message", lang))
                    .header("X-API-New-Version", "/api/v2/etudiants/statistics")
                    .body(response);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors du comptage des étudiants: " + e.getMessage());
            throw new CustomException(
                languageUtil.getMessage("error.internal.server", lang),
                CustomException.ErrorType.INTERNAL_ERROR
            );
        }
    }
}
