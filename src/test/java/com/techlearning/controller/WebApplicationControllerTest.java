package com.techlearning.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebApplicationController.class)
public class WebApplicationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_Index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome"));
    }

    @Test
    void test_Welcome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/index/{name}","Test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome Test !!"));
    }
}
