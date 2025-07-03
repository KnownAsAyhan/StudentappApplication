package com.example.studentapp.controller;

import com.example.studentapp.model.dto.StudentRequest;
import com.example.studentapp.model.dto.StudentResponse;
import com.example.studentapp.model.dto.StudentWithTeacherResponse;
import com.example.studentapp.repository.TeacherRepository;
import com.example.studentapp.service.StudentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/students")
@Validated
public class StudentController {

    private final StudentService studentService;
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public StudentController(StudentService studentService,
                             TeacherRepository teacherRepository,
                             ModelMapper modelMapper) {
        this.studentService = studentService;
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    // ✅ Get student profile photo
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getProfilePhoto(@PathVariable Long id) {
        byte[] photoData = studentService.getPhotoByStudentId(id);

        if (photoData == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(photoData);
    }

    // ✅ Get all students
    @GetMapping("")
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    // ✅ Get student by ID
    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // ✅ Create new student (with optional photo)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentResponse createStudent(
            @RequestParam("name") String name,
            @RequestParam("department") String department,
            @RequestParam("gpa") Double gpa,
            @RequestParam(value = "teacherId", required = false) Long teacherId,


            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        if (photo != null && !photo.isEmpty()) {
            String contentType = photo.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                throw new IllegalArgumentException("Only JPG and PNG images are allowed.");
            }
        }

        StudentRequest request = new StudentRequest();
        request.setName(name);
        request.setDepartment(department);
        request.setGpa(gpa);
        request.setTeacherId(teacherId);

        return studentService.createStudent(request, photo);
    }

    // ✅ Update existing student
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentResponse updateStudent(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("department") String department,
            @RequestParam("gpa") Double gpa,
            @RequestParam(value = "teacherId", required = false) Long teacherId,


            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        if (photo != null && !photo.isEmpty()) {
            String contentType = photo.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                throw new IllegalArgumentException("Only JPG and PNG images are allowed.");
            }
        }

        StudentRequest request = new StudentRequest();
        request.setName(name);
        request.setDepartment(department);
        request.setGpa(gpa);
        request.setTeacherId(teacherId);

        return studentService.updateStudent(id, request, photo);
    }

    // ✅ Delete student
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    // ✅ Get all students with their teachers
    @GetMapping("/with-teacher")
    public List<StudentWithTeacherResponse> getAllStudentsWithTeachers() {
        return studentService.getAllStudentsWithTeachers();
    }
}
