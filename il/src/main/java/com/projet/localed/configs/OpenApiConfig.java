package com.projet.localed.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projet Localed API")
                        .version("1.0")
                        .description("Documentation de l’API pour le projet Localed")
                        .contact(new Contact()
                                .name("Veysel")
                                .email("ton.email@exemple.com")));
    }
}
