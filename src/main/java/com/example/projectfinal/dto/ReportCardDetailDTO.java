package com.example.projectfinal.dto;

import com.example.projectfinal.entity.ReportCard;
import com.example.projectfinal.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCardDetailDTO {
    private int id;
    private String nameReport; // Kiểm tra gì (15', 45', cuối kỳ)
    private String semester; // Học kỳ nào (Kỳ 1, kỳ 2)
    private Date dateReport;
    private double score;

    private ReportCard reportCard;
}
