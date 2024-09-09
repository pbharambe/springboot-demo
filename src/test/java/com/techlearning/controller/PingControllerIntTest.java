package com.techlearning.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingControllerIntTest {

    @LocalServerPort
    int randomServerPort;

    RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:"+randomServerPort);
    }

    @Test
    public void shouldCallPing() {
        String pingResponse = restClient.get().uri("/ping").retrieve().body(String.class);
        assertAll(
                () -> assertNotNull(pingResponse),
                () -> assertEquals("I am alive !!", pingResponse)
        );
    }

    @Test
    public void shouldCallDetails() {
        PingController.ApplicationDetails detailsResponse = restClient.get().uri("/details").retrieve().body(PingController.ApplicationDetails.class);
        assertAll(
                () -> assertNotNull(detailsResponse),
                () -> assertEquals("Application is up!", detailsResponse.getResponse())
        );
    }

}
