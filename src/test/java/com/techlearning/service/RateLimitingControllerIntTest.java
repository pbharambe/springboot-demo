package com.techlearning.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RateLimitingControllerIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getResource_rateLimitNotExceeded_shouldReturnSuccess() {
        String url = "http://localhost:" + port + "/api/rate-limiting/resource";

        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
            assertThat(response.getBody()).isEqualTo("Resource api accessed successfully");
        }
    }

    @Test
    void getResource_rateLimitExceeded_shouldReturnRateLimitExceededMessage() {
        String url = "http://localhost:" + port + "/api/rate-limiting/resource";

//        for (int i = 0; i < 10; i++) {
//            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
//            assertThat(response.getBody()).isEqualTo("Resource api accessed successfully");
//        }

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo("Rate limit exceeded. Please Try again later.");
    }
}
