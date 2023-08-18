package com.example.projectfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report_card_detail")
public class ReportCardDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nameReport; // Kiểm tra gì (15', 45', cuối kỳ)
    private String semester; // Học kỳ nào (Kỳ 1, kỳ 2)
    private Date dateReport;
    private double score;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_report_card")
//    @JsonIgnore
    private ReportCard reportCard;

    @Override
    public String toString() {
        return "ReportCardDetail{" +
                "id=" + id +
                ", nameReport='" + nameReport + '\'' +
                ", semester='" + semester + '\'' +
                ", dateReport=" + dateReport +
                ", score=" + score +
                '}';
    }
}
