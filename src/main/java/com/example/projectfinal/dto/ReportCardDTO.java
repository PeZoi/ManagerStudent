package com.example.projectfinal.dto;

import com.example.projectfinal.entity.ReportCardDetail;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCardDTO {
    private int idReportCard;

    private List<ReportCardDetail> reportCardDetail;
    private Student student;
    private Subject subject;
}
