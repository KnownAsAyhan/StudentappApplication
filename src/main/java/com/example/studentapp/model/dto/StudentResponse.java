// src/main/java/com/example/studentapp/model/dto/StudentResponse.java
package com.example.studentapp.model.dto;

import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private String name;
    private String department;
    private Double gpa;               // Optional: include if you want to show GPA
    private String teacherName;       // Only the teacher's name to avoid recursion
}
