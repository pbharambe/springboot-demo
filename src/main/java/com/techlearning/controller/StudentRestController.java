package com.techlearning.controller;

import com.techlearning.entity.StudentEntity;
import com.techlearning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentRestController {

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
    public ResponseEntity<StudentEntity> createStudent(@RequestBody StudentEntity studentEntity) {
        return new ResponseEntity<>(service.create(studentEntity), HttpStatus.CREATED);
    }

    @GetMapping("/get/{firstName}")
    public ResponseEntity<StudentEntity> getStudent(@PathVariable("firstName") String firstName) {
        return new ResponseEntity<>(service.getStudent(firstName), HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("firstName") String firstName) {
        service.deleteStudentByName(firstName);
        return new ResponseEntity<>("Student information deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAll() {
        service.deleteStudentData();
        return new ResponseEntity<>("Deleted all Student data.", HttpStatus.OK);
    }


}
