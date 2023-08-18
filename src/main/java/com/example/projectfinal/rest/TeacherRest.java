package com.example.projectfinal.rest;

import com.example.projectfinal.dto.TeacherDTO;
import com.example.projectfinal.service.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/teacher/api")
public class TeacherRest {
    private TeacherService teacherService;

    @Autowired
    public TeacherRest(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(teacherService.getAllTeacher(), HttpStatus.OK);
    }

    @GetMapping("{idTeacher}")
    public ResponseEntity<?> findById(@PathVariable(name = "idTeacher") int idTeacher) {
        try {
            TeacherDTO teacher = teacherService.getTeacher(idTeacher);
            if (teacher == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(teacherService.getTeacher(idTeacher), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> save(@RequestParam("teacherObject") String teacherJson) {
        try {
            TeacherDTO teacherDTO = teacherService.saveTeacher(teacherJson);
            // Nếu gặp lỗi gì mà không lưu được thì sẽ trả về luôn
            if (teacherDTO == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{idTeacher}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable(name = "idTeacher") int idTeacher, @RequestParam("teacherObject") String teacherJson,
                                    @RequestParam(value = "class", required = false) String idClass,
                                    @RequestParam(value = "subject", required = false) List<Integer> idSubjects,
                                    @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        try {
            TeacherDTO teacherDTO = teacherService.updateTeacher(teacherJson, idClass, idSubjects, avatar);
            return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{idTeacher}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable(name = "idTeacher") int idTeacher) {
        try {
            TeacherDTO teacherDTO = teacherService.getTeacher(idTeacher);
            if (teacherDTO == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            teacherService.deleteTeacher(idTeacher);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
