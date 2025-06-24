package com.example.studentapp.service;

import com.example.studentapp.model.dto.TeacherRequest;
import com.example.studentapp.model.dto.TeacherResponse;
import com.example.studentapp.model.entity.Student;
import com.example.studentapp.model.entity.Teacher;
import com.example.studentapp.repository.StudentRepository;
import com.example.studentapp.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }

    public TeacherResponse createTeacher(TeacherRequest request) {
        Teacher teacher = modelMapper.map(request, Teacher.class);
        Teacher saved = teacherRepository.save(teacher);
        return convertToResponse(saved);
    }

    public List<TeacherResponse> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private TeacherResponse convertToResponse(Teacher teacher) {
        TeacherResponse response = modelMapper.map(teacher, TeacherResponse.class);
        if (teacher.getStudents() != null) {
            List<String> names = teacher.getStudents().stream()
                    .map(Student::getName)
                    .collect(Collectors.toList());
            response.setStudentNames(names);
        }
        return response;
    }

    public void assignStudents(Long teacherId, List<Long> studentIds) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        List<Student> students = studentRepository.findAllById(studentIds);

        for (Student student : students) {
            student.setTeacher(teacher); // Link student to teacher
        }

        studentRepository.saveAll(students); // Save updated students
    }
}

