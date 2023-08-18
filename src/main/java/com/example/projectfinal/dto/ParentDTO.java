package com.example.projectfinal.dto;

import com.example.projectfinal.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentDTO {
    private int idParent;
    private String nameParent;
    private String phoneNumber;

    private Student student;
}
