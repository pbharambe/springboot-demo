package com.techlearning.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {


    //@OpenAPIDefinition(info = @Info(title = "Demo API", version = "2.0", description = "Swagger API Demo"))
    @Value("${techlearning.openapi.dev-url}")
    private String devUrl;

    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-apis")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo API")
                        .version("1.0")
                        .contact(new Contact().email("pankaj.bharambe@gmail.com").name("Tech Learning"))
                        .description("Tech Learning"))
                .servers(List.of((new Server().url(devUrl).description("Development environment URL"))));
    }
}
