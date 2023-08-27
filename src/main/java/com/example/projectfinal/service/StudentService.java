package com.example.projectfinal.service;

import com.example.projectfinal.dto.StudentDTO;
import com.example.projectfinal.dto.TeacherDTO;
import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface StudentService {
    public StudentDTO getStudent(int idStudent);
    public StudentDTO getNewStudent();
    public List<StudentDTO> getAllStudents();
    public StudentDTO saveStudent(String student);
    public StudentDTO saveStudentTE(String student, int idClass);
    public StudentDTO updateStudent(String student, String parent, String idClass, MultipartFile avatar);
    public void deleteStudent(int idStudent);

    public List<StudentDTO> getAllStudentsByClass(Class classs);

    public List<StudentDTO> saveStudentDataFromExcel(MultipartFile file);
}
