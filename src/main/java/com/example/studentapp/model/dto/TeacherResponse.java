package com.example.studentapp.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class TeacherResponse {
    private Long id;
    private String name;
    private String subject;
    private List<String> studentNames; // just names, not full objects
}
