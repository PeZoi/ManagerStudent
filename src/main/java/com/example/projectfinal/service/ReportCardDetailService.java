package com.example.projectfinal.service;

import com.example.projectfinal.dto.ReportCardDetailDTO;
import com.example.projectfinal.dto.SubjectDTO;
import com.example.projectfinal.entity.ReportCardDetail;
import com.example.projectfinal.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportCardDetailService {
    public ReportCardDetailDTO getReportCardDetail(int idReportCardDetail);
    public List<ReportCardDetailDTO> getAllReportCardDetail();
    public ReportCardDetailDTO saveReportCardDetail(ReportCardDetail reportCardDetail);
    public void deleteReportCardDetail(int idReportCardDetail);
}
