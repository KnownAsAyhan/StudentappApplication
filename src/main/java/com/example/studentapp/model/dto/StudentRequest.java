package com.example.studentapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentRequest {


    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Department cannot be blank")
    private String department;

    @NotNull(message = "GPA is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "GPA must be at least 0.0")
    @DecimalMax(value = "4.0", inclusive = true, message = "GPA must be at most 4.0")
    private Double gpa;

    @Schema(description = "Optional teacher ID")
    private Long teacherId;
    // 🔄 Needed to assign a teacher during create/update
}
