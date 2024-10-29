package com.techlearning.mapper;

import com.techlearning.dto.StudentDTO;
import com.techlearning.entity.StudentEntity;
import com.techlearning.untility.StudentDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentMapperTest {

    StudentMapper studentMapper = StudentMapper.INSTANCE;

    @Test
    void test_convertStudentDtoToStudentEntity_when_input_is_null() {
        StudentDTO studentDTO = null;
        StudentEntity result = studentMapper.convertStudentDtoToStudentEntity(studentDTO);
        assertNull(result);
    }

    @Test
    void test_convertStudentEntityToStudentDTO_when_input_is_null() {
        StudentEntity studentEntity = null;
        StudentDTO result = studentMapper.convertStudentEntityToStudentDTO(studentEntity);
        assertNull(result);
    }

    @Test
    void test_convertStudentEntityToStudentDTO() {
        StudentEntity studentEntity = StudentDataBuilder.CreateStudentEntity();
        StudentDTO studentDTO = studentMapper.convertStudentEntityToStudentDTO(studentEntity);
        assertAll(
                () -> assertNotNull(studentDTO),
                () -> assertNull(studentDTO.id()),
                () -> assertEquals(studentEntity.getFirstName(), studentDTO.firstName())
        );
    }

    @Test
    void test_convertStudentDtoToStudentEntity() {
        StudentDTO studentDTO = StudentDataBuilder.CreateStudentDTO();
        StudentEntity studentEntity = studentMapper.convertStudentDtoToStudentEntity(studentDTO);
        assertAll(
                () -> assertNotNull(studentEntity),
                () -> assertEquals(studentEntity.getId(), studentDTO.id()),
                () -> assertEquals(studentEntity.getFirstName(), studentDTO.firstName())
        );
    }
}