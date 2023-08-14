package com.ltp.gradesubmission.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                                .info(new Info().title("Grade API")
                                                .description("This is an API that manages students, courses and their grades.")
                                                .version("v0.0.1"))
                                .components(new Components()
                                                .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")));
        }
}
