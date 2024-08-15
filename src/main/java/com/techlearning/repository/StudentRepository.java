package com.techlearning.repository;

import com.techlearning.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByFirstName(String firstName);

    void deleteByFirstName(String firstName);
}
