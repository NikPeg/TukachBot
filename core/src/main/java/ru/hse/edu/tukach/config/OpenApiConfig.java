package ru.hse.edu.tukach.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:pom.properties")
public class OpenApiConfig {

    @Value("${revision}")
    private String version;

    @Value("${springdoc.swagger-ui.servers}")
    private List<String> servers;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("epp-dictionary")
            .packagesToScan("ru.hse.edu.tukach")
            .pathsToMatch("/api/**")
            .build();
    }

    @Bean
    public OpenAPI docsSingleWindowOpenApi() {
        String securitySchemeName = "bearer-jwt-scheme";

        OpenAPI api = new OpenAPI()
            .components(components(securitySchemeName))
            .info(info())
            .addSecurityItem(securityRequirement(securitySchemeName));

        servers.forEach(s -> api.addServersItem(new Server().url(s)));

        return api;
    }

    private SecurityRequirement securityRequirement(String securitySchemeName) {
        return new SecurityRequirement().addList(securitySchemeName);
    }

    private Info info() {
        return new Info()
            .title("Tukach REST API")
            .version(version)
            .description("Restful API for \"Tukach\"")
            .contact(new Contact()
                .name("Tukach team")
                .url("https://edu.hse.ru/")
                .email("adarunova@edu.hse.ru"));
    }

    private Components components(String securitySchemeName) {
        return new Components()
            .addSecuritySchemes(securitySchemeName, securityScheme());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");
    }

}
