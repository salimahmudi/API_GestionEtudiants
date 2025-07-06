package com.example.etudiantapi.repository;

import com.example.etudiantapi.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    Optional<Etudiant> findByEmail(String email);

    List<Etudiant> findByNiveau(String niveau);

    boolean existsByEmail(String email);

    @Query("SELECT e FROM Etudiant e ORDER BY e.nom, e.prenom")
    List<Etudiant> findAllOrderByNomAndPrenom();
}
