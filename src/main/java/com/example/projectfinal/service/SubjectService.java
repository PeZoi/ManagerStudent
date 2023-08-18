package com.example.projectfinal.service;

import com.example.projectfinal.dto.StudentDTO;
import com.example.projectfinal.dto.SubjectDTO;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectService {
    public SubjectDTO getSubject(int idSubject);
    public List<SubjectDTO> getAllSubjects();
    public SubjectDTO saveSubject(String subject);
    public SubjectDTO updateSubject(String subject);
    public void deleteSubject(int idSubject);
}
