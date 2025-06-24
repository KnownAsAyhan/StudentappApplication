package com.example.studentapp.controller;

import com.example.studentapp.model.dto.StudentWithTeacherResponse;
import com.example.studentapp.model.dto.StudentRequest;
import com.example.studentapp.model.dto.StudentResponse;
import com.example.studentapp.model.entity.Student;
import com.example.studentapp.model.entity.Teacher;
import com.example.studentapp.repository.TeacherRepository;
import com.example.studentapp.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public StudentController(StudentService studentService, TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);  // No need to map again here
    }


    @PostMapping
    public StudentResponse createStudent(@RequestBody StudentRequest request) {
        return studentService.createStudent(request);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(@PathVariable Long id, @RequestBody StudentRequest request) {
        return studentService.updateStudent(id, request);
    }


    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/with-teacher")
    public List<StudentWithTeacherResponse> getAllStudentsWithTeachers() {
        return studentService.getAllStudentsWithTeachers();
    }
}
