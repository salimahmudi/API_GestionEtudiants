package com.example.etudiantapi.controller;

import com.example.etudiantapi.dto.EtudiantDTO;
import com.example.etudiantapi.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/etudiants")
@Tag(name = "Étudiants", description = "Gestion des étudiants")
public class EtudiantController {

    private static final Logger LOGGER = Logger.getLogger(EtudiantController.class.getName());

    @Autowired
    private EtudiantService etudiantService;

    public EtudiantController() {
        LOGGER.info("🎯 EtudiantController initialisé !");
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les étudiants")
    @ApiResponse(responseCode = "200", description = "Liste des étudiants récupérée avec succès")
    public ResponseEntity<List<EtudiantDTO>> getAllEtudiants(
            @Parameter(description = "Langue pour les messages")
            @RequestParam(defaultValue = "fr") String lang) {

        List<EtudiantDTO> etudiants = etudiantService.getAllEtudiants();
        return ResponseEntity.ok(etudiants);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un étudiant par ID")
    public ResponseEntity<EtudiantDTO> getEtudiantById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "fr") String lang) {

        Optional<EtudiantDTO> etudiant = etudiantService.getEtudiantById(id);
        return etudiant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel étudiant")
    public ResponseEntity<EtudiantDTO> createEtudiant(
            @Valid @RequestBody EtudiantDTO etudiantDTO,
            @RequestParam(defaultValue = "fr") String lang) {

        EtudiantDTO createdEtudiant = etudiantService.createEtudiant(etudiantDTO, lang);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEtudiant);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un étudiant")
    public ResponseEntity<EtudiantDTO> updateEtudiant(
            @PathVariable Long id,
            @Valid @RequestBody EtudiantDTO etudiantDTO,
            @RequestParam(defaultValue = "fr") String lang) {

        EtudiantDTO updatedEtudiant = etudiantService.updateEtudiant(id, etudiantDTO, lang);
        return ResponseEntity.ok(updatedEtudiant);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un étudiant")
    public ResponseEntity<Void> deleteEtudiant(
            @PathVariable Long id,
            @RequestParam(defaultValue = "fr") String lang) {

        etudiantService.deleteEtudiant(id, lang);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des étudiants")
    public ResponseEntity<List<EtudiantDTO>> searchEtudiants(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String niveau,
            @RequestParam(defaultValue = "fr") String lang) {

        if (email != null && !email.trim().isEmpty()) {
            Optional<EtudiantDTO> etudiant = etudiantService.getEtudiantByEmail(email);
            return ResponseEntity.ok(etudiant.map(List::of).orElse(List.of()));
        } else if (niveau != null && !niveau.trim().isEmpty()) {
            List<EtudiantDTO> etudiants = etudiantService.getEtudiantsByNiveau(niveau);
            return ResponseEntity.ok(etudiants);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Compter les étudiants")
    public ResponseEntity<Long> countEtudiants() {
        long count = etudiantService.countEtudiants();
        return ResponseEntity.ok(count);
    }
}
