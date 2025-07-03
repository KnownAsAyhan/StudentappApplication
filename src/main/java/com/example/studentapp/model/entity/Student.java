package com.example.studentapp.model.entity;

// Tells Java this is a database entity
import jakarta.persistence.*;
import lombok.Data;

@Entity // This tells Spring to treat this class as a table
@Data // Lombok adds getters, setters, to String, equals, hashCode
public class Student {

    @Id // Marks this as the "primary key" of the table (unique ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // This means the database will auto-generate the ID value
    private Long id;

    // These are the columns in your table
    private String name;
    private String department;
    private Double gpa;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


}
