package com.techlearning.service;

import com.techlearning.entity.StudentEntity;
import com.techlearning.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"student_data"})
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @CachePut(value = "student_data", key = "#root.args[0]")
    public StudentEntity create(StudentEntity studentEntity) {
        logger.info("Student Save operation.");
        return studentRepository.save(studentEntity);
    }

    @CachePut(value = "student_data", key = "#root.args[0]")
    public StudentEntity create(String firstName, String lastName) {
        logger.info("Student Save operation.");
        StudentEntity.StudentEntityBuilder builder = StudentEntity.builder();
        return studentRepository.save(builder.firstName(firstName).lastName(lastName).build());
    }

    @Cacheable(value = "student_data")
    public StudentEntity getStudent(String firstName) {
        logger.info("Fetch Student information by firstName criteria");
        return studentRepository.findByFirstName(firstName).orElse(null);
    }

    @Transactional
    @CacheEvict(value = "student_data", key = "#root.args[0]")
    public String deleteStudentByName(String firstName) {
        logger.info("Delete Student entry by firstName criteria");
        if (studentRepository.deleteByFirstName(firstName) == 1) {
            return "Records got deleted";
        }
        return "Records not deleted";
    }

    @CacheEvict(value = "student_data", allEntries = true, key = "#root.args[0]")
    public void deleteStudentData() {
        logger.info("Delete all Student entries");
        studentRepository.deleteAll();
    }
}
