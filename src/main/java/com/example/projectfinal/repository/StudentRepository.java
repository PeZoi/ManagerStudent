package com.example.projectfinal.repository;

import com.example.projectfinal.entity.School;
import com.example.projectfinal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    public Student findTopByOrderByIdStudentDesc();
}
