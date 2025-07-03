package com.example.studentapp.service;

import com.example.studentapp.model.entity.Student;
import com.example.studentapp.model.entity.Teacher;
import com.example.studentapp.model.dto.StudentRequest;
import com.example.studentapp.model.dto.StudentResponse;
import com.example.studentapp.model.dto.StudentWithTeacherResponse;
import com.example.studentapp.repository.StudentRepository;
import com.example.studentapp.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    public StudentResponse createStudent(StudentRequest request, MultipartFile photo) throws IOException {
        Student student = modelMapper.map(request, Student.class);

        if (request.getTeacherId() != null) {
            Optional<Teacher> teacherOptional = teacherRepository.findById(request.getTeacherId());
            teacherOptional.ifPresent(student::setTeacher);
        }

        if (photo != null && !photo.isEmpty()) {
            student.setPhotoData(photo.getBytes());
        }

        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentResponse.class);
    }

    public StudentResponse updateStudent(Long id, StudentRequest request, MultipartFile photo) throws IOException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.setName(request.getName());
        student.setDepartment(request.getDepartment());
        student.setGpa(request.getGpa());

        if (request.getTeacherId() != null) {
            Optional<Teacher> teacherOptional = teacherRepository.findById(request.getTeacherId());
            teacherOptional.ifPresent(student::setTeacher);
        } else {
            student.setTeacher(null); // clear relation if null passed
        }

        if (photo != null && !photo.isEmpty()) {
            student.setPhotoData(photo.getBytes());
        }

        Student updatedStudent = studentRepository.save(student);
        return modelMapper.map(updatedStudent, StudentResponse.class);
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return modelMapper.map(student, StudentResponse.class);
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());
    }

    public List<StudentWithTeacherResponse> getAllStudentsWithTeachers() {
        return studentRepository.findAll()
                .stream()
                .map(student -> {
                    StudentWithTeacherResponse dto = modelMapper.map(student, StudentWithTeacherResponse.class);
                    if (student.getTeacher() != null) {
                        dto.setTeacherName(student.getTeacher().getName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    public byte[] getPhotoByStudentId(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        return student.getPhotoData();
    }
}
