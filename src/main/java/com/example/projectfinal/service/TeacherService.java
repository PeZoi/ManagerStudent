package com.example.projectfinal.service;

import com.example.projectfinal.dto.StudentDTO;
import com.example.projectfinal.dto.TeacherDTO;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.entity.Subject;
import com.example.projectfinal.entity.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface TeacherService {
    public TeacherDTO getTeacher(int idTeacher);
    public TeacherDTO getNewTeacher();
    public List<TeacherDTO> getAllTeacher();
    public TeacherDTO saveTeacher(String teacher);
    public TeacherDTO updateTeacher(String teacher, String idClass, List<Integer> idSubjects, MultipartFile avatar);
    public void deleteTeacher(int idTeacher);
}
