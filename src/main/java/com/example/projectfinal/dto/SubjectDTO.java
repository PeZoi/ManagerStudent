package com.example.projectfinal.dto;

import com.example.projectfinal.entity.Teacher;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private int idSubject;
    private String nameSubject;

//    private List<Teacher> teachers;
}
