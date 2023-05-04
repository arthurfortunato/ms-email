package com.ms.email.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "E-mail API", version = "2.0.0", description = "Microservice for sending email using Spring Email and Gmail's SMTP server."))
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi emailApi() {
        String[] packagesToScan = {"com.ms.email.swagger"};
        return GroupedOpenApi.builder()
                .group("email")
                .packagesToScan(packagesToScan)
                .build();
    }
}
