package com.techlearning.untility;

import com.techlearning.dto.StudentDTO;
import com.techlearning.entity.StudentEntity;

public class StudentDataBuilder {

    public static StudentEntity CreateStudentEntity() {
        return new StudentEntity(1,"John", "Smith");
    }

    public static StudentDTO CreateStudentDTO() {
        return new StudentDTO(1l, "John", "Smith");
    }
}
