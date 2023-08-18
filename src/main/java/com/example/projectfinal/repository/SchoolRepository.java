package com.example.projectfinal.repository;

import com.example.projectfinal.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
    public School findTopByOrderByIdSchoolDesc();
}
