package ru.chernov.currency_conversion_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerDescription() {
        return new OpenAPI()
                .info(new Info()
                        .title("Конвертер валют")
                        .description("API конвертера валют")
                        .version("0.0.1"));
    }
}
