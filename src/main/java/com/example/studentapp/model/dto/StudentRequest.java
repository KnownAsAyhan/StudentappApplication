package com.example.studentapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StudentRequest {
    private String name;
    private String department;
    private Double gpa;
    @Schema(description = "Optional teacher ID")
    private Long teacherId;
    // 🔄 Needed to assign a teacher during create/update
}
