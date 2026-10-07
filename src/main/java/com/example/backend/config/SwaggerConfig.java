package com.example.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger / OpenAPI configuration.
 * This bean makes the API documentation available at:
 *   http://localhost:8080/swagger-ui.html
 * and the raw OpenAPI JSON at:
 *   http://localhost:8080/v3/api-docs
 *
 * If you later add security (e.g., JWT), you can uncomment the security
 * sections below and adjust the scheme accordingly.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Groups all REST endpoints under a single OpenAPI document.
     * You can create multiple groups (e.g., by package or path) if your
     * application grows large.
     */
    @Bean
    public GroupedOpenApi todoApi() {
        return GroupedOpenApi.builder()
                .group("todo-api")                     // logical name shown in Swagger UI
                .pathsToMatch("/api/**")              // include everything under /api
                .build();
    }

    /**
     * Root OpenAPI object – holds metadata like title, version, description.
     * Security sections are commented out for now; uncomment when you add
     * authentication (e.g., JWT) to the API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo List API")
                        .description("REST API for a simple Todo‑List application built with Spring Boot, SQLite and React.")
                        .version("1.0.0"));
        // -----------------------------------------------------------------
        // Example of how to add a JWT bearer token security scheme later:
        // -----------------------------------------------------------------
        // .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        // .components(new Components()
        //         .addSecuritySchemes("Bearer Authentication",
        //                 new SecurityScheme()
        //                         .name("Bearer Authentication")
        //                         .type(SecurityScheme.Type.HTTP)
        //                         .scheme("bearer")
        //                         .bearerFormat("JWT")));
    }
}
