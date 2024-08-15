package com.techlearning.untility;

import com.techlearning.entity.StudentEntity;

public class StudentDataBuilder {

    public static StudentEntity CreateStudentEntity() {
        return new StudentEntity(1, "John", "Smith");
    }
}
