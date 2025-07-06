package com.example.etudiantapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestion des Étudiants / Student Management API")
                        .version("2.0")
                        .description("API REST pour la gestion des étudiants avec support multilingue et versioning.\n" +
                                   "REST API for student management with multilingual support and versioning.\n\n" +
                                   "📌 **Versions disponibles / Available versions:**\n" +
                                   "- V1 (⚠️ Deprecated): `/api/v1/etudiants`\n" +
                                   "- V2 (Current): `/api/v2/etudiants`\n\n" +
                                   "🌐 **Langues supportées / Supported languages:**\n" +
                                   "- Français (fr)\n" +
                                   "- English (en)\n\n" +
                                   "💡 **Utilisation / Usage:**\n" +
                                   "Ajoutez le paramètre `?lang=fr` ou `?lang=en` à vos requêtes pour obtenir les messages dans la langue souhaitée.\n" +
                                   "Add the parameter `?lang=fr` or `?lang=en` to your requests to get messages in the desired language.")
                        .contact(new Contact()
                                .name("Support API / API Support")
                                .email("support@etudiantapi.com")
                                .url("https://github.com/example/etudiant-api"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Serveur de développement / Development Server"),
                        new Server().url("https://api.example.com").description("Serveur de production / Production Server")
                ))
                .tags(List.of(
                        new Tag().name("Étudiants V1 (Deprecated)")
                                .description("⚠️ Version 1 dépréciée - Utilisez la version 2 (/api/v2/etudiants)\n" +
                                           "⚠️ Version 1 deprecated - Use version 2 (/api/v2/etudiants)"),
                        new Tag().name("Étudiants V2")
                                .description("Gestion des étudiants - Version 2 avec support multilingue amélioré\n" +
                                           "Student management - Version 2 with enhanced multilingual support"),
                        new Tag().name("Home")
                                .description("Endpoints généraux / General endpoints"),
                        new Tag().name("Database")
                                .description("Gestion de la base de données / Database management")
                ));
    }
}
