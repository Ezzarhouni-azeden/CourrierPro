package com.courrierpro.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Spring Boot avec Swagger")
                        .version("1.0")
                        .description("Documentation de l'API avec Swagger"));
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
