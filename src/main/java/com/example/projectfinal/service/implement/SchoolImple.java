package com.example.projectfinal.service.implement;

import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.School;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.repository.ClassRepository;
import com.example.projectfinal.repository.SchoolRepository;
import com.example.projectfinal.repository.StudentRepository;
import com.example.projectfinal.service.ClassService;
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
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ClassService classService;
    @Autowired
    private StudentRepository studentRepository;

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
        try{
            Optional<School> school = schoolRepository.findById(idSchool);
            List<Class> classList = classRepository.findClassBySchool(school.get());
            for (Class aClass : classList) {
                // Set student học lớp chuẩn bị xoá thành null
                List<Student> students = studentRepository.findStudentsByClasss(aClass);
                for (Student student : students) {
                    student.setClasss(null);
                    studentRepository.save(student);
                }
            }

            schoolRepository.deleteById(idSchool);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
