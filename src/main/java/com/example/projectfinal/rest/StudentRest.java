package com.example.projectfinal.rest;

import com.example.projectfinal.dto.StudentDTO;
import com.example.projectfinal.dto.TeacherDTO;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student/api")
public class StudentRest {
    private StudentService studentService;

    public StudentRest(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{idStudent}")
    public ResponseEntity<?> findById(@PathVariable(name = "idStudent") int idStudent) {
        try{
            StudentDTO student = studentService.getStudent(idStudent);
            if(student == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(studentService.getStudent(idStudent), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> save(@RequestParam("studentObject") String studentJson) {
        try {
            StudentDTO studentDTO = studentService.saveStudent(studentJson);
            // Nếu gặp lỗi gì mà không lưu được thì sẽ trả về luôn
            if (studentDTO == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save-te")
    @Transactional
    public ResponseEntity<?> saveTE(@RequestParam("studentObject") String studentJson, @RequestParam("idClass") int idClass) {
        try {
            StudentDTO studentDTO = studentService.saveStudentTE(studentJson, idClass);
            // Nếu gặp lỗi gì mà không lưu được thì sẽ trả về luôn
            if (studentDTO == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{idStudent}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable(name = "idStudent") int idStudent, @RequestParam("studentObject") String studentJson,
                                    @RequestParam(value = "parentObject", required = false) String parentJson,
                                    @RequestParam(value = "class", required = false) String idClass,
                                    @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        try {
            StudentDTO studentDTO = studentService.updateStudent(studentJson, parentJson, idClass, avatar);
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{idStudent}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable(name = "idStudent") int idStudent) {
        try {
            StudentDTO studentDTO = studentService.getStudent(idStudent);
            if (studentDTO == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            studentService.deleteStudent(idStudent);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/upload-students-data")
    @Transactional
    public ResponseEntity<?> uploadStudentExcel(@RequestParam("file-data") MultipartFile file) {
        try{
            List<StudentDTO> studentsDTOs = studentService.saveStudentDataFromExcel(file);
            return ResponseEntity.ok(Map.of("success", true, "students", studentsDTOs));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Map.of("success", false));
        }
    }
}
