package com.example.studentapp.service;

import com.example.studentapp.model.Student;
import com.example.studentapp.model.dto.StudentResponse;
import com.example.studentapp.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Tells Spring to treat this as a service class
public class StudentService {

    @Autowired
    private ModelMapper modelMapper;
    private final StudentRepository studentRepository;


    public StudentResponse convertToResponse(Student student) {
        return modelMapper.map(student, StudentResponse.class);
    }

    // Constructor injection (Spring gives us the repository)
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll(); // SELECT * FROM student
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null); // SELECT * WHERE id=?
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student); // INSERT or UPDATE
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existing = studentRepository.findById(id);
        if (existing.isPresent()) {
            Student s = existing.get();
            s.setName(updatedStudent.getName());
            s.setDepartment(updatedStudent.getDepartment());
            s.setGpa(updatedStudent.getGpa());
            return studentRepository.save(s); // Save updated data
        }
        return null;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id); // DELETE FROM student WHERE id=?
    }
}
