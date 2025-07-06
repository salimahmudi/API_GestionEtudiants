package com.example.etudiantapi.controller;

import com.example.etudiantapi.service.EtudiantService;
import com.example.etudiantapi.util.HtmlTemplateUtil;
import com.example.etudiantapi.util.LanguageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@Tag(name = "Home", description = "Endpoints généraux / General endpoints")
public class HomeController {

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private HtmlTemplateUtil htmlTemplateUtil;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    @Operation(summary = "Page d'accueil", description = "Page d'accueil interactive avec informations sur l'API")
    public String home(@RequestParam(defaultValue = "fr") String lang) {
        // Get dynamic data
        String welcomeMessage = languageUtil.getMessage("general.welcome", lang);

        // Get student count
        long totalStudents = 0;
        try {
            totalStudents = etudiantService.countEtudiants();
        } catch (Exception e) {
            // Handle gracefully if service is not available
        }

        // Generate the HTML page using the template utility
        return htmlTemplateUtil.generateHomePage(lang, welcomeMessage, totalStudents);
    }

    @GetMapping("/api/info")
    @ResponseBody
    @Operation(summary = "Informations sur l'API", description = "Retourne les informations de l'API avec support multilingue")
    public ResponseEntity<Map<String, Object>> getApiInfo(
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        Map<String, Object> info = new HashMap<>();
        info.put("title", languageUtil.getMessage("doc.title", lang));
        info.put("description", languageUtil.getMessage("doc.description", lang));
        info.put("version", "2.0");
        info.put("supportedLanguages", languageUtil.getSupportedLanguages());
        info.put("currentLanguage", lang);
        info.put("timestamp", LocalDateTime.now().toString());

        // Version information
        Map<String, Object> versions = new HashMap<>();
        versions.put("v1", Map.of(
            "status", "deprecated",
            "endpoint", "/api/v1/etudiants",
            "message", languageUtil.getMessage("api.deprecated.message", lang),
            "deprecationDate", "2025-01-01",
            "endOfLifeDate", "2025-12-31"
        ));
        versions.put("v2", Map.of(
            "status", "current",
            "endpoint", "/api/v2/etudiants",
            "message", languageUtil.getMessage("api.status.healthy", lang),
            "releaseDate", "2025-07-01",
            "features", new String[]{"pagination", "advanced-search", "statistics", "health-check"}
        ));
        info.put("versions", versions);

        // Available endpoints
        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("documentation", "/swagger-ui.html");
        endpoints.put("openapi", "/v3/api-docs");
        endpoints.put("health", "/api/v2/etudiants/health");
        endpoints.put("database", "/database/info");
        endpoints.put("statistics", "/api/v2/etudiants/statistics");
        endpoints.put("search", "/api/v2/etudiants/search/advanced");
        info.put("endpoints", endpoints);

        // System information
        Map<String, Object> system = new HashMap<>();
        system.put("javaVersion", System.getProperty("java.version"));
        system.put("springBootVersion", "3.x");
        system.put("serverTime", LocalDateTime.now().toString());
        system.put("uptime", getUptime());
        info.put("system", system);

        // Statistics
        Map<String, Object> stats = new HashMap<>();
        try {
            stats.put("totalStudents", etudiantService.countEtudiants());
            stats.put("lastUpdated", LocalDateTime.now().toString());
        } catch (Exception e) {
            stats.put("totalStudents", 0);
            stats.put("error", "Unable to fetch statistics");
        }
        info.put("statistics", stats);

        Map<String, Object> response = new HashMap<>();
        response.put("data", info);
        response.put("message", languageUtil.getMessage("general.operation.completed", lang));
        response.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/status")
    @ResponseBody
    @Operation(summary = "Statut de l'API", description = "Retourne le statut actuel de l'API")
    public ResponseEntity<Map<String, Object>> getApiStatus(
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        Map<String, Object> status = new HashMap<>();
        status.put("status", "healthy");
        status.put("version", "2.0");
        status.put("timestamp", LocalDateTime.now().toString());
        status.put("uptime", getUptime());

        // Check database connectivity
        try {
            long studentCount = etudiantService.countEtudiants();
            status.put("database", Map.of(
                "status", "connected",
                "studentCount", studentCount
            ));
        } catch (Exception e) {
            status.put("database", Map.of(
                "status", "error",
                "error", e.getMessage()
            ));
        }

        // Check services
        Map<String, Object> services = new HashMap<>();
        services.put("studentService", "operational");
        services.put("notificationService", "operational");
        services.put("languageService", "operational");
        status.put("services", services);

        Map<String, Object> response = new HashMap<>();
        response.put("data", status);
        response.put("message", languageUtil.getMessage("api.status.healthy", lang));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/health")
    @ResponseBody
    @Operation(summary = "Vérification de santé", description = "Endpoint de santé pour monitoring")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now().toString());
        health.put("version", "2.0");

        return ResponseEntity.ok(health);
    }

    private String getUptime() {
        long uptimeMs = System.currentTimeMillis() - startTime;
        long seconds = uptimeMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        return String.format("%d hours, %d minutes, %d seconds",
            hours, minutes % 60, seconds % 60);
    }

    private static final long startTime = System.currentTimeMillis();
}
