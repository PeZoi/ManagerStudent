package com.example.projectfinal.repository;

import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.School;
import com.example.projectfinal.entity.Teacher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    public Class findTopByOrderByIdClassDesc();
    public Optional<Class> findClassByTeacher(Teacher teacher);
    public List<Class> findClassBySchool(School school);
}
