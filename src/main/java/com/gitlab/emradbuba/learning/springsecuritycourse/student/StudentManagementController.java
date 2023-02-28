package com.gitlab.emradbuba.learning.springsecuritycourse.student;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("management/api/v1/students")
@Slf4j
public class StudentManagementController {
    private static final List<Student> STUDENT_LIST = Stream.of(
            new Student(1, "James Bond"),
            new Student(2, "Maria Jones"),
            new Student(3, "Anna Smith")
    ).toList();

    @GetMapping
    public List<Student> getAllStudents() {
        return new ArrayList<>(STUDENT_LIST);
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        log.info("Registering a student: " + student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteNewStudent(@PathVariable("studentId") Integer studentId) {
        log.info("Deleting a student id={}", studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateNewStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        log.info("Updating a student id={} ==> {}", studentId, student);
    }
}
