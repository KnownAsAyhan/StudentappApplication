package com.example.studentapp.repository;

import com.example.studentapp.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

// <Student, Long> means:
// Student = Entity class
// Long = Type of the primary key
public interface StudentRepository extends JpaRepository<Student, Long> {
}
