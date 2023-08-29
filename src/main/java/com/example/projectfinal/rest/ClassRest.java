package com.example.projectfinal.rest;

import com.example.projectfinal.dto.ClassDTO;
import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.School;
import com.example.projectfinal.entity.Teacher;
import com.example.projectfinal.repository.TeacherRepository;
import com.example.projectfinal.service.ClassService;
import com.example.projectfinal.service.SchoolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/class/api")
public class ClassRest {
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private ClassService classService;
    private SchoolService schoolService;
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    public ClassRest(ModelMapper modelMapper, ObjectMapper objectMapper, ClassService classService, SchoolService schoolService) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.classService = classService;
        this.schoolService = schoolService;
    }

    @GetMapping("/list")
    public List<ClassDTO> classList() {
        List<Class> classList = classService.getAllClasses();
        List<ClassDTO> classDTOList = new ArrayList<>();
        for (Class classs : classList) {
            ClassDTO classDTO = modelMapper.map(classs, ClassDTO.class);
            classDTOList.add(classDTO);
        }
        return classDTOList;
    }

    @GetMapping("/{idClass}")
    public ResponseEntity<?> getClass(@PathVariable int idClass) {
        try {
            Optional<Class> classOptional = classService.getClass(idClass);
            if (classOptional.isPresent()) {
                // Chuyển thành classDTO
                ClassDTO classDTO = modelMapper.map(classOptional.get(), ClassDTO.class);
                return new ResponseEntity<>(classDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> saveClass(@RequestParam("classObject") String classJson, @RequestParam("school") int idSchool) {
        try {
            Class classs = objectMapper.readValue(classJson, Class.class);
            Optional<School> school = schoolService.getSchool(idSchool);
            if (school.isEmpty()) {
                return new ResponseEntity<>("Not found id school", HttpStatus.NOT_FOUND);
            }
            classs.setSchool(school.get());
            Class savedClass = classService.saveClass(classs);

            // Sử dụng ModelMapper để chuyển đổi Class thành Map
            ClassDTO classDTO = modelMapper.map(savedClass, ClassDTO.class);

            return new ResponseEntity<>(classDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

        @PutMapping("/update/{idClass}")
        public ResponseEntity<?> updateClass(@PathVariable int idClass, @RequestParam("classObject") String classJson, @RequestParam("school") int idSchool) {
            try {
                Class classs = objectMapper.readValue(classJson, Class.class);
                Optional<School> school = schoolService.getSchool(idSchool);
                if (school.isEmpty()) {
                    return new ResponseEntity<>("Not found id school", HttpStatus.NOT_FOUND);
                }
                ClassDTO classDTO = classService.updateClass(classs, idSchool);


                if (classDTO != null) {
                    return new ResponseEntity<>(classDTO, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @DeleteMapping("/delete/{idClass}")
    @Transactional
    public ResponseEntity<?> deleteClass(@PathVariable Integer idClass) {
        try{
            Optional<Class> classOptional = classService.getClass(idClass);
            if (classOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            classService.deleteClass(idClass);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
