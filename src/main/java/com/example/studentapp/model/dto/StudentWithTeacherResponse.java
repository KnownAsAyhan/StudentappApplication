package com.example.studentapp.model.dto;

import lombok.Data;

@Data
public class StudentWithTeacherResponse {
    private Long id;
    private String name;
    private String department;
    private Double gpa;
    private String teacherName;
}
