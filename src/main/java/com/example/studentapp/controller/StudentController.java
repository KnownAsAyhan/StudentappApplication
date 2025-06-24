package com.example.studentapp.controller;

import com.example.studentapp.model.Student;
import com.example.studentapp.model.dto.StudentRequest;
import com.example.studentapp.model.dto.StudentResponse;
import com.example.studentapp.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Tells Spring this class handles HTTP requests
@RequestMapping("/students") // All endpoints start with /students
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    // Spring injects the service automatically
    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return modelMapper.map(student, StudentResponse.class);
    }


    @PostMapping
    public Student createStudent(@RequestBody StudentRequest request) {
//        Student student = new Student();
//        student.setName(request.getName());
//        student.setDepartment(request.getDepartment());
//        student.setGpa(request.getGpa());
        Student student = modelMapper.map(request, Student.class); // modelMapper using
        return studentService.createStudent(student);
    }


    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody StudentRequest request) {
        Student student = modelMapper.map(request, Student.class);
        return studentService.updateStudent(id, student);
    }


    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
