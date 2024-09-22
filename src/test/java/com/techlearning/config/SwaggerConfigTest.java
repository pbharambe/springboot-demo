package com.techlearning.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SwaggerConfigTest.TestConfig.class)
class SwaggerConfigTest {

    @Autowired
    OpenAPI openAPI;

    @Autowired
    GroupedOpenApi groupedOpenApi;

    @Test
    void testOpenAPICustomConfiguration() {
        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getInfo()).isNotNull();
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Demo API");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("1.0");
        assertThat(openAPI.getInfo().getContact().getName()).isEqualTo("Tech Learning");
        assertThat(openAPI.getServers().size()).isEqualTo(1);
        assertThat(openAPI.getServers().get(0).getDescription()).isEqualTo("Development environment URL");
    }

    @Test
    void testGroupedOpenApiConfiguration() {
        assertNotNull(groupedOpenApi, "GroupedOpenApi should not be null");
        assertThat(groupedOpenApi).isNotNull();
        assertThat(groupedOpenApi.getGroup()).isEqualTo("public-apis");
        assertThat(groupedOpenApi.getPathsToMatch()).isNotIn(Arrays.asList("/**"));
    }

    @Configuration
    static class TestConfig extends SwaggerConfig {
        @Bean
        public String devUrl() {
            return "http://localhost:8080";
        }
    }

}