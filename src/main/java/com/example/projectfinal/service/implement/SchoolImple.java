package com.example.projectfinal.service.implement;

import com.example.projectfinal.entity.School;
import com.example.projectfinal.repository.SchoolRepository;
import com.example.projectfinal.service.SchoolService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolImple implements SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public Optional<School> getSchool(int idSchool) {
        return schoolRepository.findById(idSchool);
    }

    @Override
    public School getNewSchool() {
        return schoolRepository.findTopByOrderByIdSchoolDesc();
    }

    @Override
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }
    @Transactional
    @Override
    public School saveSchool(School school) {
        return schoolRepository.saveAndFlush(school);
    }

    @Transactional
    @Override
    public void deleteSchool(int idSchool) {
        schoolRepository.deleteById(idSchool);
    }
}
