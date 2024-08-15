package com.techlearning.service;

import com.techlearning.entity.StudentEntity;
import com.techlearning.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames={"student_data"})
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @CachePut(value = "student_data", key = "#root.args[0]")
    public StudentEntity create(String firstName, String lastName) {
        StudentEntity.StudentEntityBuilder builder = StudentEntity.builder();
        return studentRepository.save(builder.firstName(firstName).lastName(lastName).build());
    }

    @Cacheable(value = "student_data")
    public StudentEntity getStudent(String firstName) {
        return studentRepository.findByFirstName(firstName).get();
    }

    @Transactional
    @CacheEvict(value="student_data", key="#root.args[0]")
    public void deleteStudentByName(String firstName) {
        studentRepository.deleteByFirstName(firstName);
    }

    @CacheEvict(value="student_data", allEntries = true, key="#root.args[0]")
    public void deleteStudentData() {
        studentRepository.deleteAll();
    }

    public StudentEntity create(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }
}
