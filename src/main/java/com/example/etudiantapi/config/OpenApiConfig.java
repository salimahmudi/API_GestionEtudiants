package com.example.etudiantapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestion des Étudiants")
                        .version("1.0")
                        .description("API pour la gestion des étudiants avec service de notifications")
                        .contact(new Contact()
                                .name("Support API")
                                .email("support@gmail.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Serveur de développement")
                ));
    }
}
