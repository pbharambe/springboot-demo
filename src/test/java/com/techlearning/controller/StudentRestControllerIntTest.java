package com.techlearning.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techlearning.entity.StudentEntity;
import com.techlearning.service.StudentService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.stream.Stream;

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

    //StudentEntity studentEntity;

    @BeforeEach
    public void init() {
        //studentEntity = StudentDataBuilder.CreateStudentEntity();
        restClient = RestClient.create("http://localhost:"+randomServerPort);
    }

    public static Stream<StudentEntity> studentEntityProvider() {
        return Stream.of(
                new StudentEntity(1,"John", "Doe"),
                new StudentEntity(2, "Jane", "Smith"),
                new StudentEntity(3, "Alice", "Johnson")
        );
    }

    @ParameterizedTest
    @MethodSource("studentEntityProvider")
    @DisplayName("Create Student")
    @Order(1)
    public void test_CreateStudent(StudentEntity studentEntity) throws JsonProcessingException {
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
        StudentEntity studentEntity = studentEntityProvider().filter(s -> s.getFirstName().equals("John")).findFirst().get();
        assertAll(
                () -> assertEquals(HttpStatus.OK, returnStr.getStatusCode()),
                () -> assertEquals(objectMapper.writeValueAsString(studentEntity), returnStr.getBody())
        );
    }

    @Test
    @DisplayName("Delete John's record")
    @Order(3)
    public void test_DeleteSingleRecord() {
        ResponseEntity<String> returnStr = restClient.delete().uri("/delete?firstName=John").retrieve().toEntity(String.class);
        assertAll(
                () -> assertEquals(HttpStatus.OK, returnStr.getStatusCode()),
                () -> assertEquals("Student information deleted.", returnStr.getBody())
        );
    }

    @Test
    @DisplayName("Delete Student Information")
    @Order(4)
    public void test_DeleteAll() {
        ResponseEntity<String> returnStr =  restClient.delete().uri("/delete-all")
                .retrieve().toEntity(String.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, returnStr.getStatusCode()),
                () -> assertEquals("Deleted all Student data.", returnStr.getBody())
        );
    }
}
