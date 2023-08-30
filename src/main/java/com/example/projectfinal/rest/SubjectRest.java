package com.example.projectfinal.rest;

import com.example.projectfinal.dto.ClassDTO;
import com.example.projectfinal.dto.SubjectDTO;
import com.example.projectfinal.dto.TeacherDTO;
import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.School;
import com.example.projectfinal.entity.Subject;
import com.example.projectfinal.service.SubjectService;
import com.example.projectfinal.service.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/subject/api")
public class SubjectRest {
    private SubjectService subjectService;
    @Autowired
    public SubjectRest(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(subjectService.getAllSubjects(), HttpStatus.OK);
    }

    @GetMapping("/{idSubject}")
    public ResponseEntity<?> findById(@PathVariable(name = "idSubject") int idSubject) {
        try{
            SubjectDTO subject = subjectService.getSubject(idSubject);
            if(subject == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(subjectService.getSubject(idSubject), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> save(@RequestParam("subjectObject") String subjectJson) {
        try{
            SubjectDTO subjectDTO = subjectService.saveSubject(subjectJson);
            // Nếu gặp lỗi gì mà không lưu được thì sẽ trả về luôn
            if(subjectDTO == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{idSubject}")
    @Transactional
    public ResponseEntity<?> updateSubject(@PathVariable(name = "idSubject") int idSubject, @RequestParam("subjectObject") String subjectJson) {
        try {
            SubjectDTO subjectDTO = subjectService.updateSubject(subjectJson);
            return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{idSubject}")
    @Transactional
    public ResponseEntity<?> deleteSubject(@PathVariable(name = "idSubject") int idSubject) {
        try{
            SubjectDTO subject = subjectService.getSubject(idSubject);
            if (subject == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            subjectService.deleteSubject(idSubject);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
