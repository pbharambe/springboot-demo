package com.techlearning.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techlearning.entity.StudentEntity;
import com.techlearning.service.StudentService;
import com.techlearning.untility.StudentDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentRestController.class)
public class StudentRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService service;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test_CreateStudent() throws Exception {
        StudentEntity studentEntity = StudentDataBuilder.CreateStudentEntity();
        // Mock the service layer
        when(service.create(any(StudentEntity.class))).thenReturn(studentEntity);

        // Perform the POST request and validate the response
        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentEntity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value(studentEntity.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(studentEntity.getLastName()));
    }

    @Test
    public void test_GetStudentData() throws Exception {
        StudentEntity studentEntity = StudentDataBuilder.CreateStudentEntity();

        when(service.getStudent(anyString())).thenReturn(studentEntity);

        mockMvc.perform(get("/get/{firstName}", "John")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(studentEntity.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(studentEntity.getLastName()));
    }

}
