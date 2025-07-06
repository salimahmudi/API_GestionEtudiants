package com.example.etudiantapi.controller;

import com.example.etudiantapi.util.LanguageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Database", description = "Gestion de la base de données / Database management")
public class DatabaseController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LanguageUtil languageUtil;

    @GetMapping("/database/info")
    @Operation(summary = "Informations sur la base de données", description = "Retourne les informations sur la base de données avec support multilingue")
    @ApiResponse(responseCode = "200", description = "Informations récupérées avec succès")
    public ResponseEntity<Map<String, Object>> getDatabaseInfo(
            @Parameter(description = "Langue pour les messages (fr/en)")
            @RequestParam(defaultValue = "fr") String lang) {

        Map<String, Object> info = new HashMap<>();

        try (Connection conn = dataSource.getConnection()) {
            info.put("url", conn.getMetaData().getURL());
            info.put("username", conn.getMetaData().getUserName());
            info.put("driverName", conn.getMetaData().getDriverName());
            info.put("databaseProduct", conn.getMetaData().getDatabaseProductName());
            info.put("databaseVersion", conn.getMetaData().getDatabaseProductVersion());

            // Compter les étudiants
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM etudiants");
                if (rs.next()) {
                    info.put("studentCount", rs.getInt("count"));
                }
            }

            // Lister les tables
            List<String> tables = new ArrayList<>();
            ResultSet rs = conn.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            info.put("tables", tables);

            // Informations sur les versions d'API
            Map<String, Object> apiInfo = new HashMap<>();
            apiInfo.put("currentVersion", "2.0");
            apiInfo.put("supportedVersions", List.of("1.0 (deprecated)", "2.0"));
            apiInfo.put("deprecationNotice", languageUtil.getMessage("api.deprecated.message", lang));
            info.put("apiVersions", apiInfo);

        } catch (Exception e) {
            info.put("error", e.getMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", info);
        response.put("message", languageUtil.getMessage("general.operation.completed", lang));
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/db-info")
    @Operation(summary = "Informations sur la base de données (Legacy)", description = "⚠️ DEPRECATED - Utilisez /database/info")
    @ApiResponse(responseCode = "200", description = "Informations récupérées avec succès")
    @Deprecated
    public Map<String, Object> getDatabaseInfoLegacy() {
        Map<String, Object> info = new HashMap<>();

        try (Connection conn = dataSource.getConnection()) {
            info.put("url", conn.getMetaData().getURL());
            info.put("username", conn.getMetaData().getUserName());
            info.put("driverName", conn.getMetaData().getDriverName());

            // Compter les étudiants
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM etudiants");
                if (rs.next()) {
                    info.put("studentCount", rs.getInt("count"));
                }
            }

            // Lister les tables
            List<String> tables = new ArrayList<>();
            ResultSet rs = conn.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            info.put("tables", tables);

        } catch (Exception e) {
            info.put("error", e.getMessage());
        }

        return info;
    }
}
