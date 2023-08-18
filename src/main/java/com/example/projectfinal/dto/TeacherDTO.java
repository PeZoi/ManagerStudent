package com.example.projectfinal.dto;

import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private int idTeacher;
    private String nameTeacher;
    private Date dateOfBirth;
    private String phoneNumber;
    private String address;
    private String email;
    private String avatar;

    private Class classs;
    private List<Subject> subjects;
}
