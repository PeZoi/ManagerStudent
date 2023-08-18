package com.example.projectfinal.service;

import com.example.projectfinal.dto.ClassDTO;
import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.School;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ClassService {
    public Optional<Class> getClass(int idClass);
    public Class getNewClass();
    public List<Class> getAllClasses();
    public Class saveClass(Class classs);
    public ClassDTO updateClass(Class classs, int idSchool);
    public void deleteClass(Integer idClass);
}
