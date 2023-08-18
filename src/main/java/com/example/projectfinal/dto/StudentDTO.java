package com.example.projectfinal.dto;

import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.Parent;
import com.example.projectfinal.entity.ReportCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private int idStudent;
    private String nameStudent;
    private Date dateOfBirth;
    private String phoneNumber;
    private String address;
    private String email;
    private String avatar;

    private Class classs;
    private Parent parent;
    private List<ReportCard> reportCards;
}
