package com.example.etudiantapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EtudiantApiApplication {

    public static void main(String[] args) {
        System.out.println("🚀 Démarrage de l'API Étudiants...");
        SpringApplication.run(EtudiantApiApplication.class, args);
        System.out.println("✅ API disponible sur http://localhost:8080");
        System.out.println("📚 Test simple : http://localhost:8080/test");
        System.out.println("📚 Documentation Swagger : http://localhost:8080/swagger-ui.html");
    }

    @GetMapping("/test")
    public String test() {
        return "✅ L'API fonctionne ! Testez maintenant les endpoints: /api/v1/etudiants";
    }
}
