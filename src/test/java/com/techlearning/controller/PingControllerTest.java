package com.techlearning.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PingController.class)
class PingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BuildProperties buildProperties;

    @InjectMocks
    PingController pingController;

    @BeforeEach
    public void setUp() {
        this.pingController = new PingController();
        mockMvc = MockMvcBuilders.standaloneSetup(pingController).build();
    }

    @Test
    public void verify_ping() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("I am alive !!"));
    }

    @Test
    public void validate_details() throws Exception {
        String expected = "{\"response\":\"Application is up!\",\"applicationVersion\":\"1.0\"}";
        when(buildProperties.getVersion()).thenReturn("1.0");
        pingController.setBuildProperties(buildProperties);

        //MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/details")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        //Assert.assertEquals(expected, mvcResult.getResponse().getContentAsString());

        Assertions.assertAll(
                () -> mockMvc.perform(get("/details"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(expected)));

    }
}
