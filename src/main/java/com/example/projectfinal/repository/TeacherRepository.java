package com.example.projectfinal.repository;

import com.example.projectfinal.entity.School;
import com.example.projectfinal.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    public Teacher findTopByOrderByIdTeacherDesc();
}
