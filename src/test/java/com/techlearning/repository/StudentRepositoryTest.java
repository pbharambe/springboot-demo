package com.techlearning.repository;

import com.techlearning.entity.StudentEntity;
import com.techlearning.untility.StudentDataBuilder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.assertj.core.api.Assertions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    @DisplayName("Test 1:Save Employee Test")
    @Order(1)
    @Rollback(value = false)
    public void testSaveStudent() {
        StudentEntity studentEntity = StudentDataBuilder.CreateStudentEntity();
        studentRepository.save(studentEntity);
        Assertions.assertThat(studentEntity.getId()).isGreaterThan(0)   ;
    }

    @Test
    @Order(2)
    public void test_GetStudent() {
        StudentEntity studentEntity = studentRepository.findById(1L).get();
        Assertions.assertThat(studentEntity.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void test_DeleteByFirstName() {
        int result = studentRepository.deleteByFirstName("John");
        Optional<StudentEntity> studentEntity = studentRepository.findById(1L);
        assertAll(
                () -> assertTrue(studentEntity.isEmpty()),
                () -> assertEquals(1, result)
        );
    }
}