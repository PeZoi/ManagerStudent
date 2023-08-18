package com.example.projectfinal.rest;

import com.example.projectfinal.dto.ParentDTO;
import com.example.projectfinal.dto.SubjectDTO;
import com.example.projectfinal.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parent/api")
public class ParentRest {
    private ParentService parentService;
    @Autowired

    public ParentRest(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(parentService.getAllParents(), HttpStatus.OK);
    }

    @GetMapping("{idParent}")
    public ResponseEntity<?> findById(@PathVariable(name = "idParent") int idParent) {
        try{
            ParentDTO parent = parentService.getParent(idParent);
            if(parent == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(parentService.getParent(idParent), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
