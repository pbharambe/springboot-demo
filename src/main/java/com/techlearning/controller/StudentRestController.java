package com.techlearning.controller;

import com.techlearning.entity.StudentEntity;
import com.techlearning.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentRestController {

    Logger logger = LoggerFactory.getLogger(StudentRestController.class);

    @Autowired
    private StudentService service;

    /*@GetMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        service.create(firstName, lastName);
        return new ResponseEntity<>("Student Information Added.", HttpStatus.CREATED);
    }*/

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StudentEntity> createStudent(@RequestBody @Valid StudentEntity studentEntity) {
        logger.info("Create new Student record in database.");
        return new ResponseEntity<>(service.create(studentEntity), HttpStatus.CREATED);
    }

    @GetMapping("/get/{firstName}")
    public ResponseEntity<StudentEntity> getStudent(@PathVariable("firstName") @Valid String firstName) {
        logger.info("Retrieve Student by firstName");
        return new ResponseEntity<>(service.getStudent(firstName), HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("firstName") @Valid String firstName) {
        service.deleteStudentByName(firstName);
        logger.info("Delete Student entry by firstName");
        return new ResponseEntity<>("Student information deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAll() {
        logger.info("Delete all Student records.");
        service.deleteStudentData();
        return new ResponseEntity<>("Deleted all Student data.", HttpStatus.OK);
    }
}
