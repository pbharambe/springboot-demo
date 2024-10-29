package com.techlearning.mapper;

import com.techlearning.dto.StudentDTO;
import com.techlearning.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentEntity convertStudentDtoToStudentEntity(StudentDTO studentDTO);

    @Mapping(target = "id", ignore = true)
    StudentDTO convertStudentEntityToStudentDTO(StudentEntity studentEntity);
}
