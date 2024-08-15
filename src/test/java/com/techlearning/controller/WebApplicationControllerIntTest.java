package com.techlearning.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebApplicationControllerIntTest {

    @LocalServerPort
    int randomServerPort;

    RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:"+randomServerPort);
    }

    @Test
    void shouldCallWelcome() {
        ResponseEntity<String> returnStr = restClient.get().uri("/index/Test").retrieve().toEntity(String.class);
        assertAll(
                () -> assertEquals(HttpStatus.OK, returnStr.getStatusCode()),
                () -> assertEquals("Welcome Test !!", returnStr.getBody())
        );
    }

    @Test
    void shouldCallIndex() {
        String retunrStr = restClient.get().uri("/").retrieve().toEntity(String.class).getBody().toString();
        assertEquals("Welcome", retunrStr);
    }


}
