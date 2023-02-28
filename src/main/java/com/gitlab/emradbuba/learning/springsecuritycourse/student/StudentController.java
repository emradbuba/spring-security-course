package com.gitlab.emradbuba.learning.springsecuritycourse.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<StudentBasicInfoDto> STUDENT_BASIC_INFO_DTO_LIST = Stream.of(
            new StudentBasicInfoDto(1, "James Bond"),
            new StudentBasicInfoDto(2, "Maria Jones"),
            new StudentBasicInfoDto(3, "Anna Smith")
            ).toList();

    @GetMapping
    public List<StudentBasicInfoDto> getStudents() {
        return STUDENT_BASIC_INFO_DTO_LIST.stream().toList();
    }
}
