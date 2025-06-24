package com.example.studentapp.model.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class TeacherRequest {


    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Subject is required")
    private String subject;
}
