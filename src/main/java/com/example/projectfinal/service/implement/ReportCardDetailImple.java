package com.example.projectfinal.service.implement;

import com.example.projectfinal.dto.ReportCardDetailDTO;
import com.example.projectfinal.dto.SubjectDTO;
import com.example.projectfinal.entity.ReportCardDetail;
import com.example.projectfinal.entity.Subject;
import com.example.projectfinal.repository.ReportCardDetailRepository;
import com.example.projectfinal.repository.SubjectRepository;
import com.example.projectfinal.service.ReportCardDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportCardDetailImple implements ReportCardDetailService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    private ReportCardDetailRepository reportCardDetailRepository;
    @Autowired
    public ReportCardDetailImple(ReportCardDetailRepository reportCardDetailRepository) {
        this.reportCardDetailRepository = reportCardDetailRepository;
    }
    @Override
    public ReportCardDetailDTO getReportCardDetail(int idReportCardDetail) {
        Optional<ReportCardDetail> reportCardDetail = reportCardDetailRepository.findById(idReportCardDetail);
        if (reportCardDetail.isEmpty()) {
            return null;
        } else {
            ReportCardDetailDTO reportCardDetailDTO = modelMapper.map(reportCardDetail.get(), ReportCardDetailDTO.class);
            return reportCardDetailDTO;
        }
    }

    @Override
    public List<ReportCardDetailDTO> getAllReportCardDetail() {
        List<ReportCardDetail> reportCardDetails = reportCardDetailRepository.findAll();
        List<ReportCardDetailDTO> reportCardDetailDTOs = new ArrayList<>();
        for (ReportCardDetail reportCardDetail : reportCardDetails) {
            ReportCardDetailDTO reportCardDetailDTO = modelMapper.map(reportCardDetail, ReportCardDetailDTO.class);
            reportCardDetailDTOs.add(reportCardDetailDTO);
        }
        return reportCardDetailDTOs;
    }

    @Override
    public ReportCardDetailDTO saveReportCardDetail(ReportCardDetail reportCardDetail) {
        return null;
    }

    @Override
    public void deleteReportCardDetail(int idReportCardDetail) {

    }
}
