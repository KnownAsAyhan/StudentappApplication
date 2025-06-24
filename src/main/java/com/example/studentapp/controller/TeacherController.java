package com.example.studentapp.controller;

import com.example.studentapp.model.dto.TeacherRequest;
import com.example.studentapp.model.dto.TeacherResponse;
import com.example.studentapp.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public TeacherResponse createTeacher(@RequestBody @Valid TeacherRequest request) {
        return teacherService.createTeacher(request);
    }

    @GetMapping
    public List<TeacherResponse> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PutMapping("/{teacherId}/students")
    public void assignStudentsToTeacher(@PathVariable Long teacherId, @RequestBody List<Long> studentIds) {
        teacherService.assignStudents(teacherId, studentIds);
    }

}
