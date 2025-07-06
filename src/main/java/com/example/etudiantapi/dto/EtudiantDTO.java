package com.example.etudiantapi.dto;

import com.example.etudiantapi.entity.Etudiant;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Objet de transfert de données pour un étudiant")
public class EtudiantDTO {

    @Schema(description = "Identifiant unique de l'étudiant", example = "1")
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Schema(description = "Nom de famille de l'étudiant", example = "HAMMOUDI", required = true)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    @Schema(description = "Prénom de l'étudiant", example = "Salima", required = true)
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Schema(description = "Adresse email de l'étudiant", example = "salimahammoudi1@gmail.com", required = true)
    private String email;

    @NotNull(message = "La date de naissance est obligatoire")
    @Schema(description = "Date de naissance de l'étudiant", example = "2003-03-27", required = true)
    private LocalDate dateNaissance;

    @NotBlank(message = "Le niveau est obligatoire")
    @Schema(description = "Niveau d'études de l'étudiant", example = "Master 1", required = true)
    private String niveau;

    @Schema(description = "Numéro de téléphone de l'étudiant", example = "0695439176")
    private String telephone;

    @Schema(description = "Date de création du dossier étudiant")
    private LocalDateTime dateCreation;

    @Schema(description = "Date de dernière modification du dossier")
    private LocalDateTime dateModification;

    // Constructeurs
    public EtudiantDTO() {}

    public EtudiantDTO(Etudiant etudiant) {
        this.id = etudiant.getId();
        this.nom = etudiant.getNom();
        this.prenom = etudiant.getPrenom();
        this.email = etudiant.getEmail();
        this.dateNaissance = etudiant.getDateNaissance();
        this.niveau = etudiant.getNiveau();
        this.telephone = etudiant.getTelephone();
        this.dateCreation = etudiant.getDateCreation();
        this.dateModification = etudiant.getDateModification();
    }

    public Etudiant toEntity() {
        Etudiant etudiant = new Etudiant();
        etudiant.setId(this.id);
        etudiant.setNom(this.nom);
        etudiant.setPrenom(this.prenom);
        etudiant.setEmail(this.email);
        etudiant.setDateNaissance(this.dateNaissance);
        etudiant.setNiveau(this.niveau);
        etudiant.setTelephone(this.telephone);
        return etudiant;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}
