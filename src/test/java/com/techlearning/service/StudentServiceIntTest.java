package com.techlearning.service;

import com.techlearning.entity.StudentEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceIntTest {

    @Autowired
    private StudentService service;

    public static Stream<StudentEntity> studentEntityProvider() {
        return Stream.of(
                new StudentEntity(1,"John", "Doe"),
                new StudentEntity(2, "Jane", "Smith"),
                new StudentEntity(3, "Alice", "Johnson")
        );
    }

    @ParameterizedTest
    @MethodSource("studentEntityProvider")
    @Order(1)
    @DisplayName("Save student data in DB")
    public void test_CreateStudent(StudentEntity studentEntity) {
        StudentEntity result = service.create(studentEntity);
        Assertions.assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(studentEntity.getFirstName(), result.getFirstName())
        );
    }

    @Test
    @Order(2)
    @DisplayName("Get John's Data")
    public void test_GetStudent_ForGivenName() {
        StudentEntity result = service.getStudent("John");
        Assertions.assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(studentEntityProvider().findFirst().get().getFirstName(), result.getFirstName())
        );
    }

    @Test
    @Order(2)
    public void test_GetStudent_ForNotFoundRecord() {
        StudentEntity result = service.getStudent("john");
        Assertions.assertAll(
                () -> assertNull(result)
        );
    }

    @Test
    @Order(3)
    @DisplayName("Delete John's data")
    public void test_DeleteStudent_ForGivenName() {
        String returnVal = service.deleteStudentByName("John");
        Assertions.assertAll(
                () -> assertNotNull(returnVal),
                () -> assertEquals("Records got deleted", returnVal)
        );
    }

    @Test
    @Order(3)
    public void test_DeleteStudent_ForNotFoundRecord() {
        String returnVal = service.deleteStudentByName("john");
        Assertions.assertAll(
                () -> assertNotNull(returnVal),
                () -> assertEquals("Records not deleted", returnVal)
        );
    }
}
