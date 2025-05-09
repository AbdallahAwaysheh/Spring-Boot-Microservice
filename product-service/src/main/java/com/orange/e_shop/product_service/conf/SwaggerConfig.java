package com.orange.e_shop.product_service.conf;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public @Bean OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Client BFF Service API - Microservices")
                        .description("This API serves as the Backend For Frontend (BFF) layer for mobile applications. It provides essential data services to the mobile team, facilitating the integration and efficient data handling for multiple clients. The API abstracts the complexities of interacting with different client-specific services, offering a unified interface for mobile app development.")
                        .version("v1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("clientAuth"))
                .components(new Components()
                        .addSecuritySchemes("auth", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer").in(SecurityScheme.In.HEADER).name("Authentication").description("Authentication token - Bearer token"))

                );
    }
}