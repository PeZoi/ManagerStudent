package com.example.projectfinal.dto;

import com.example.projectfinal.entity.School;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
    private int idClass;
    private String nameClass;
    private String groupName; // khối học
    private int schoolYear; // niên khoá
    private int enrollment; // sĩ số

    private School school;
    private List<Student> students;
    private Teacher teacher;
}
