package com.example.studentapp.service;

import com.example.studentapp.model.dto.StudentRequest;
import com.example.studentapp.model.dto.StudentResponse;
import com.example.studentapp.model.dto.StudentWithTeacherResponse;
import com.example.studentapp.model.entity.Student;
import com.example.studentapp.model.entity.Teacher;
import com.example.studentapp.repository.StudentRepository;
import com.example.studentapp.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private ModelMapper modelMapper;

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public StudentResponse convertToResponse(Student student) {
        StudentResponse dto = new StudentResponse();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setDepartment(student.getDepartment());
        dto.setGpa(student.getGpa());
        if (student.getTeacher() != null) {
            dto.setTeacherName(student.getTeacher().getName());
        }
        return dto;
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::convertToResponse)
                .orElse(null);
    }
    public Student getStudentEntityById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }


    public StudentResponse createStudent(StudentRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setDepartment(request.getDepartment());
        student.setGpa(request.getGpa());

        if (request.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            student.setTeacher(teacher);
        }

        Student saved = studentRepository.save(student);
        return convertToResponse(saved);
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(request.getName());
        student.setDepartment(request.getDepartment());
        student.setGpa(request.getGpa());

        if (request.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            student.setTeacher(teacher);
        }

        Student saved = studentRepository.save(student);
        return convertToResponse(saved);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentWithTeacherResponse> getAllStudentsWithTeachers() {
        return studentRepository.findAll().stream()
                .map(student -> {
                    StudentWithTeacherResponse dto = new StudentWithTeacherResponse();
                    dto.setId(student.getId());
                    dto.setName(student.getName());
                    dto.setDepartment(student.getDepartment());
                    dto.setGpa(student.getGpa());
                    if (student.getTeacher() != null) {
                        dto.setTeacherName(student.getTeacher().getName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
