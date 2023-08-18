package com.example.projectfinal.rest;

import com.example.projectfinal.dto.ReportCardDetailDTO;
import com.example.projectfinal.dto.SubjectDTO;
import com.example.projectfinal.entity.ReportCard;
import com.example.projectfinal.entity.ReportCardDetail;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.repository.ReportCardDetailRepository;
import com.example.projectfinal.repository.ReportCardRepository;
import com.example.projectfinal.repository.StudentRepository;
import com.example.projectfinal.service.ReportCardDetailService;
import com.example.projectfinal.service.ReportCardService;
import com.example.projectfinal.service.SubjectService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/report-card-detail/api")
public class ReportCardDetailRest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReportCardDetailRepository reportCardDetailRepository;
    @Autowired
    private ReportCardRepository reportCardRepository;
    @Autowired
    private StudentRepository studentRepository;
    private ReportCardDetailService reportCardDetailService;
    @Autowired
    public ReportCardDetailRest(ReportCardDetailService reportCardDetailService) {
        this.reportCardDetailService = reportCardDetailService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(reportCardDetailService.getAllReportCardDetail(), HttpStatus.OK);
    }

    @GetMapping("/{idReportCardDetail}")
    public ResponseEntity<?> findById(@PathVariable(name = "idReportCardDetail") int idReportCardDetail) {
        try{
            ReportCardDetailDTO reportCardDetail = reportCardDetailService.getReportCardDetail(idReportCardDetail);
            if(reportCardDetail == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(reportCardDetailService.getReportCardDetail(idReportCardDetail), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam("reportCardDetail") String rp_details) {
        try {
            // Chuyển đổi từ list ReportCardDetail json sang list java
            List<ReportCardDetail> reportCardDetails = objectMapper.readValue(rp_details, new TypeReference<List<ReportCardDetail>>() {});
            // Lấy ra report card của các reportCardDetails
            Optional<ReportCard> reportCard = reportCardRepository.findById(reportCardDetails.get(0).getReportCard().getIdReportCard());
            // Lấy ra student của thằng reportCard
            Optional<Student> student = studentRepository.findById(reportCard.get().getStudent().getIdStudent());
            // Update lại các list ReportCardDetail
            reportCardDetailRepository.saveAll(reportCardDetails);
            // Không hiểu sao tự nhiên update lại xong thì report card nó lại set student về null
            // Nên phải set student lại
            reportCard.get().setStudent(student.get());
            // Update lại report card vừa set lại student
            reportCardRepository.saveAndFlush(reportCard.get());
            return new ResponseEntity<>(reportCardDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
