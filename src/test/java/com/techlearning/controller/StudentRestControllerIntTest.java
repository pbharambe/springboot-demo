package com.techlearning.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techlearning.entity.StudentEntity;
import com.techlearning.service.StudentService;
import com.techlearning.untility.StudentDataBuilder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentRestControllerIntTest {

    @LocalServerPort
    int randomServerPort;

    RestClient restClient;

    private StudentService studentService = new StudentService();

    @Autowired
    private ObjectMapper objectMapper;

    StudentEntity studentEntity;

    @BeforeEach
    public void init() {
        studentEntity = StudentDataBuilder.CreateStudentEntity();
        restClient = RestClient.create("http://localhost:"+randomServerPort);
    }

    @Test
    @DisplayName("Create Student")
    @Order(1)
    public void test_CreateStudent() throws JsonProcessingException {
        ResponseEntity<String> returnStr =  restClient.post().uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(studentEntity))
                .retrieve().toEntity(String.class);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, returnStr.getStatusCode()),
                () -> assertEquals(objectMapper.writeValueAsString(studentEntity), returnStr.getBody())
        );
    }

    @Test
    @DisplayName("Retrieve Student By FirstName")
    @Order(2)
    public void test_GetStudent() {
        ResponseEntity<String> returnStr =  restClient.get().uri("/get/John")
                .retrieve().toEntity(String.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, returnStr.getStatusCode()),
                () -> assertEquals(objectMapper.writeValueAsString(studentEntity), returnStr.getBody())
        );
    }

    @Test
    @DisplayName("Delete Student Information")
    @Order(3)
    public void test_DeleteAll() {
        ResponseEntity<String> returnStr =  restClient.delete().uri("/delete-all")
                .retrieve().toEntity(String.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, returnStr.getStatusCode()),
                () -> assertEquals("Deleted all Student data.", returnStr.getBody())
        );
    }
}
