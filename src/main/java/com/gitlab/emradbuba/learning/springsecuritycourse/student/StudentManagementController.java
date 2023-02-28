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
    private static final List<StudentFullInfoDto> STUDENT_BASIC_INFO_DTO_LIST = Stream.of(
            new StudentFullInfoDto(1, "James Bond", "11-225-8-44", "90022545114"),
            new StudentFullInfoDto(2, "Maria Jones", "81-595-9-488", "8801114114"),
            new StudentFullInfoDto(3, "Anna Smith", "777-15-8-794", "5112584744")
    ).toList();

    @GetMapping
    public List<StudentFullInfoDto> getAllStudents() {
        return new ArrayList<>(STUDENT_BASIC_INFO_DTO_LIST);
    }

    @PostMapping
    public void registerNewStudent(@RequestBody StudentFullInfoDto studentFullInfoDto) {
        log.info("Registering a student: " + studentFullInfoDto);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteNewStudent(@PathVariable("studentId") Integer studentId) {
        log.info("Deleting a student id={}", studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateNewStudent(@PathVariable("studentId") Integer studentId, @RequestBody StudentFullInfoDto studentFullInfoDto) {
        log.info("Updating a student id={} ==> {}", studentId, studentFullInfoDto);
    }
}
