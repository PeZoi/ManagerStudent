package com.example.projectfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report_card")
public class ReportCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReportCard;

    @OneToMany(mappedBy = "reportCard",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReportCardDetail> reportCardDetail;

    @ManyToOne (fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_student")
    @JsonIgnore
    private Student student;

    @OneToOne (cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "id_subject")
    private Subject subject;

    @Override
    public String toString() {
        return "ReportCard{" +
                "idReportCard=" + idReportCard +
                ", idSubject=" + subject +
                '}';
    }
}
