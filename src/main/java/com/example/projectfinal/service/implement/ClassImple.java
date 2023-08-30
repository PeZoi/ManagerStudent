package com.example.projectfinal.service.implement;

import com.example.projectfinal.dto.ClassDTO;
import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.School;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.repository.ClassRepository;
import com.example.projectfinal.repository.SchoolRepository;
import com.example.projectfinal.repository.StudentRepository;
import com.example.projectfinal.repository.TeacherRepository;
import com.example.projectfinal.service.ClassService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassImple implements ClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;


    @Override
    public Optional<Class> getClass(int idClass) {
        return classRepository.findById(idClass);
    }

    @Override
    public Class getNewClass() {
        return classRepository.findTopByOrderByIdClassDesc();
    }

    @Override
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    @Override
    @Transactional
    public Class saveClass(Class classs) {
        return classRepository.saveAndFlush(classs);
    }

    @Override
    @Transactional
    public ClassDTO updateClass(Class classs, int idSchool) {
        try {
            Optional<Class> classOptional = classRepository.findById(classs.getIdClass());

            Optional<School> schoolOptional = schoolRepository.findById(idSchool);
            classs.setSchool(schoolOptional.get());
            classs.setTeacher(classOptional.get().getTeacher());

            Class classSave = classRepository.save(classs);
            // Chuyển thành dto để trả về
            ClassDTO classDTO = modelMapper.map(classSave, ClassDTO.class);
            return classDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteClass(Integer idClass) {
        try {
            Optional<Class> classOptional = classRepository.findById(idClass);

            // Set student học lớp chuẩn bị xoá thành null
            List<Student> students = studentRepository.findStudentsByClasss(classOptional.get());
            for (Student student : students) {
                student.setClasss(null);
                studentRepository.save(student);
            }

//            Class classs = classRepository.save(classOptional.get());

            classRepository.delete(classOptional.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
