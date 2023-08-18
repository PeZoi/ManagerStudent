package com.example.projectfinal.dto;

import com.example.projectfinal.entity.Class;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTO {
    private int idSchool;
    private String nameSchool;
    private String addressSchool;
    private String phoneNumberSchool;
    private String emailSchool;
    private String avatarSchool;

    private List<Class> classes;
}