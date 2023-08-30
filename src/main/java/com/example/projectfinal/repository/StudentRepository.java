package com.example.projectfinal.repository;

import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.School;
import com.example.projectfinal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    public Student findTopByOrderByIdStudentDesc();
    public List<Student> findStudentsByClasss(Class classs);


}
