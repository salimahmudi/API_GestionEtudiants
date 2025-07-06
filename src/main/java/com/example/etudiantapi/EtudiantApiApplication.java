package com.example.etudiantapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EtudiantApiApplication {

    public static void main(String[] args) {
        System.out.println("🚀 Démarrage de l'API Étudiants... / Starting Student API...");
        SpringApplication.run(EtudiantApiApplication.class, args);
        System.out.println("✅ API disponible sur / API available at: http://localhost:8080");
        System.out.println("📚 Test simple / Simple test: http://localhost:8080/test");
        System.out.println("📚 Documentation Swagger: http://localhost:8080/swagger-ui.html");
        System.out.println("🌐 API Info (FR): http://localhost:8080/api/info?lang=fr");
        System.out.println("🌐 API Info (EN): http://localhost:8080/api/info?lang=en");
        System.out.println("🆕 API V2 (Current): http://localhost:8080/api/v2/etudiants");
        System.out.println("⚠️ API V1 (Deprecated): http://localhost:8080/api/v1/etudiants");
    }

    @GetMapping("/test")
    public String test() {
        return "✅ L'API fonctionne ! / API is working!\n" +
               "🆕 Testez la version 2: /api/v2/etudiants\n" +
               "⚠️ Version 1 (dépréciée): /api/v1/etudiants\n" +
               "🌐 Support multilingue: ajoutez ?lang=fr ou ?lang=en\n" +
               "📚 Documentation: /swagger-ui.html";
    }
}
