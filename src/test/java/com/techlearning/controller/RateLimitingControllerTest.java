package com.techlearning.controller;

import com.techlearning.service.RateLimitingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RateLimitingController.class)
class RateLimitingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateLimitingService rateLimitingService;

    @Test
    void getResource_rateLimitNotExceeded_shouldReturnSuccess() throws Exception {
        when(rateLimitingService.allowRequest("test-api-key")).thenReturn(true);
        mockMvc.perform(get("/api/rate-limiting/resource"))
                .andExpect(status().isOk())
                .andExpect(content().string("Resource api accessed successfully"));
    }

    @Test
    void getResource_rateLimitExceeded_shouldReturnRateLimitExceededMessage() throws Exception {
        when(rateLimitingService.allowRequest("test-api-key")).thenReturn(false);
        mockMvc.perform(get("/api/rate-limiting/resource"))
                .andExpect(status().isOk())
                .andExpect(content().string("Rate limit exceeded. Please Try again later."));
    }
}