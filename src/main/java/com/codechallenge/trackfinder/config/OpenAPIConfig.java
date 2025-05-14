package com.codechallenge.trackfinder.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAPIConfig {

    @Value("${springdoc.version:1.0.0}")
    private String appVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Track Finder API")
                        .description("API for searching music track information using ISRC codes")
                        .version(appVersion)
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("csanvega")
                        )
                );
    }
}