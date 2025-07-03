package com.example.studentapp.controller;

import com.example.studentapp.model.dto.StudentWithTeacherResponse;
import com.example.studentapp.model.dto.StudentRequest;
import com.example.studentapp.model.dto.StudentResponse;
import com.example.studentapp.model.entity.Student;
import com.example.studentapp.model.entity.Teacher;
import com.example.studentapp.repository.TeacherRepository;
import com.example.studentapp.service.StudentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@Validated
public class StudentController {

    private final StudentService studentService;
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public StudentController(StudentService studentService, TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }


    @PostMapping(value = "/{id}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfilePhoto(
            @PathVariable Long id,
            @Parameter(description = "Profile photo file") @RequestPart("file") MultipartFile file
    ) {
        try {
            String fileName = id + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads/" + fileName);
            Files.createDirectories(uploadPath.getParent());
            Files.write(uploadPath, file.getBytes());

            return ResponseEntity.ok("Photo uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload photo");
        }
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getProfilePhoto(@PathVariable Long id) {
        try {
            // Find file by ID pattern
            Path uploadsDir = Paths.get("uploads");
            Optional<Path> photoPath = Files.list(uploadsDir)
                    .filter(path -> path.getFileName().toString().startsWith(id + "_"))
                    .findFirst();

            if (photoPath.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(photoPath.get().toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or detect dynamically
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
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
    public StudentResponse createStudent(@RequestBody @Valid StudentRequest request) {
        return studentService.createStudent(request);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(@PathVariable Long id, @RequestBody @Valid StudentRequest request) {
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
