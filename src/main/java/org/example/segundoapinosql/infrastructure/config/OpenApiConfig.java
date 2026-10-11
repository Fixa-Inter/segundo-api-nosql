package org.example.segundoapinosql.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI segundoApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fixa API - Checklists (NoSQL)")
                        .description("API de modelos de checklist e inspeções de equipamentos, construida com SQL e MongoDB.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Fixa")
                                .email("fixa.inter2026@gmail.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                )

                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
