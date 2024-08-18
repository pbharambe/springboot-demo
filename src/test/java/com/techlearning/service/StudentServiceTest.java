package com.techlearning.service;

import com.techlearning.entity.StudentEntity;
import com.techlearning.repository.StudentRepository;
import com.techlearning.untility.StudentDataBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    private StudentEntity studentEntity;

    @Mock
    Logger logger;

    @BeforeEach
    public void init() {
        studentEntity = StudentDataBuilder.CreateStudentEntity();
    }

    @Test
    @Order(1)
    public void test_Create_forSuccessfull()  {
        when(studentRepository.save(any())).thenReturn(studentEntity);
        StudentEntity result = studentService.create(studentEntity);
        Assertions.assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(studentEntity.getFirstName(), result.getFirstName()),
                () -> verify(logger, times(1)).info("Student Save operation.")
        );
    }

    @Test
    @Order(2)
    public void test_GetStudent_forGivenData() {
        Optional<StudentEntity> optionalStudent = Optional.of(studentEntity);
        when(studentRepository.findByFirstName(anyString())).thenReturn(optionalStudent);
        StudentEntity result = studentService.getStudent("John");
        Assertions.assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(studentEntity.getFirstName(), result.getFirstName()),
                () -> verify(logger, times(1)).info("Fetch Student information by firstName criteria")
        );
    }

    @Test
    @Order(3)
    public void test_Delete_forGivenData() {
        studentService.deleteStudentByName("John");
        Optional<StudentEntity> studentEntity = studentRepository.findById(1L);
        Assertions.assertAll(
                () -> verify(logger, times(1)).info("Delete Student entry by firstName criteria"),
                () -> assertTrue(studentEntity.isEmpty())
        );
    }
}