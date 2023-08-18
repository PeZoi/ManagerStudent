package com.example.projectfinal.service;

import com.example.projectfinal.entity.School;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SchoolService {
    public Optional<School> getSchool(int idSchool);
    public School getNewSchool();
    public List<School> getAllSchools();
    public School saveSchool(School school);
    public void deleteSchool(int idSchool);
}
