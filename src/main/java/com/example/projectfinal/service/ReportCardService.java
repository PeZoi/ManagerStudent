package com.example.projectfinal.service;

import com.example.projectfinal.dto.ReportCardDTO;
import com.example.projectfinal.dto.ReportCardDetailDTO;
import com.example.projectfinal.entity.ReportCard;
import com.example.projectfinal.entity.ReportCardDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportCardService {
    public ReportCardDTO getReportCard (int idReportCard);
    public List<ReportCardDTO> getAllReportCard();
    public ReportCardDTO saveReportCard(ReportCard reportCard);
    public void deleteReportCard(int idReportCard);

    public List<ReportCardDTO> getReportCardByIdStudent(int idStudent);
}
