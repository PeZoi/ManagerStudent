package com.example.projectfinal.service.implement;

import com.example.projectfinal.dto.ReportCardDTO;
import com.example.projectfinal.entity.ReportCard;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.repository.ReportCardRepository;
import com.example.projectfinal.repository.StudentRepository;
import com.example.projectfinal.service.ReportCardService;
import com.example.projectfinal.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportCardImple implements ReportCardService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StudentRepository studentRepository;
    private ReportCardRepository reportCardRepository;

    @Autowired
    public ReportCardImple(ReportCardRepository reportCardRepository) {
        this.reportCardRepository = reportCardRepository;
    }

    @Override
    public ReportCardDTO getReportCard(int idReportCard) {
        Optional<ReportCard> reportCard = reportCardRepository.findById(idReportCard);
        if (reportCard.isEmpty()) {
            return null;
        } else {
            ReportCardDTO reportCardDTO = modelMapper.map(reportCard.get(), ReportCardDTO.class);
            return reportCardDTO;
        }
    }

    @Override
    public List<ReportCardDTO> getAllReportCard() {
        List<ReportCard> reportCards = reportCardRepository.findAll();
        List<ReportCardDTO> reportCardDTOs = new ArrayList<>();
        for (ReportCard reportCard : reportCards) {
            ReportCardDTO reportCardDTO = modelMapper.map(reportCard, ReportCardDTO.class);
            reportCardDTOs.add(reportCardDTO);
        }
        return reportCardDTOs;
    }

    @Override
    public ReportCardDTO saveReportCard(ReportCard reportCard) {
        return null;
    }

    @Override
    public void deleteReportCard(int idReportCard) {

    }

    @Override
    public List<ReportCardDTO> getReportCardByIdStudent(int idStudent) {
        Optional<Student> studentOptional = studentRepository.findById(idStudent);
        if (studentOptional.isPresent()) {
            List<ReportCard> reportCards = reportCardRepository.findByStudent(studentOptional.get());
            List<ReportCardDTO> reportCardDTOs = new ArrayList<>();
            for (ReportCard reportCard : reportCards) {
                ReportCardDTO reportCardDTO = modelMapper.map(reportCard, ReportCardDTO.class);
                reportCardDTOs.add(reportCardDTO);
            }
            return reportCardDTOs;
        }
        return null;
    }
}
