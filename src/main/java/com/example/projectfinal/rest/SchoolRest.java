package com.example.projectfinal.rest;

import com.example.projectfinal.dto.ClassDTO;
import com.example.projectfinal.dto.SchoolDTO;
import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.School;
import com.example.projectfinal.service.FileUploadService;
import com.example.projectfinal.service.SchoolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/school/api")
public class SchoolRest {
    private final SchoolService schoolService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    public SchoolRest(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/list")
    public List<SchoolDTO> schoolList() {
        List<School> schoolList = schoolService.getAllSchools();
        List<SchoolDTO> schoolDTOList = new ArrayList<>();
        for (School school : schoolList) {
            SchoolDTO schoolDTO = modelMapper.map(school, SchoolDTO.class);
            schoolDTOList.add(schoolDTO);
        }
        return schoolDTOList;
    }

    @GetMapping("/{idSchool}")
    public ResponseEntity<?> getSchool(@PathVariable int idSchool) {
        try {
            Optional<School> schoolOptional = schoolService.getSchool(idSchool);
            if (schoolOptional.isPresent()) {
                // Chuyển thành schoolDTO
                SchoolDTO schoolDTO = modelMapper.map(schoolOptional.get(), SchoolDTO.class);
                return new ResponseEntity<>(schoolDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<School> saveSchool(@RequestParam("schoolObject") String schoolJson, @RequestParam("avatar") MultipartFile multipartFile) {
        try {
            School school = objectMapper.readValue(schoolJson, School.class);
            schoolService.saveSchool(school);
            school = schoolService.getNewSchool();
            String avatar = fileUploadService.uploadFile(multipartFile, "school_" + school.getIdSchool());
            school.setAvatarSchool(avatar);
            return new ResponseEntity<>(schoolService.saveSchool(school), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{idSchool}")
    public ResponseEntity<?> updateSchool(@PathVariable int idSchool, @RequestParam("schoolObject") String schoolJson, @RequestParam(value = "avatar", required = false) MultipartFile multipartFile) {
        try {
            School school = objectMapper.readValue(schoolJson, School.class);
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String avatar = fileUploadService.uploadFile(multipartFile, "school_" + school.getIdSchool());
                school.setAvatarSchool(avatar);
            }

            schoolService.saveSchool(school);

            // Chuyển thành dto để trả về
            SchoolDTO schoolDTO = modelMapper.map(school, SchoolDTO.class);

            return new ResponseEntity<>(schoolDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{idSchool}")
    public ResponseEntity<?> deleteSchool(@PathVariable int idSchool) {
        try{
            Optional<School> schoolOptional = schoolService.getSchool(idSchool);
            if (schoolOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            schoolService.deleteSchool(schoolOptional.get().getIdSchool());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
