package com.kokuu.edukaizen.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI custom() {
        return new OpenAPI()
                .info(new Info()
                        .title("EduKaizen Documentation")
                        .version("1.0.0"))
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer Authentication",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .bearerFormat("JWT")
                                                .scheme("bearer")))
                .security(List.of(new SecurityRequirement().addList("Bearer Authentication")))
                .paths(new Paths()
                        .addPathItem("/api/auth/login",
                                new PathItem().post(new Operation().security(List.of())))
                        .addPathItem("/api/auth/register",
                                new PathItem().post(new Operation().security(List.of()))));
    }
}
