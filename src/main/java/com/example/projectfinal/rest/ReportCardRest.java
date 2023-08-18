package com.example.projectfinal.rest;

import com.example.projectfinal.dto.ReportCardDTO;
import com.example.projectfinal.service.ReportCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report-card/api")
public class ReportCardRest {
    private ReportCardService reportCardService;
    @Autowired
    public ReportCardRest(ReportCardService reportCardService) {
        this.reportCardService = reportCardService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(reportCardService.getAllReportCard(), HttpStatus.OK);
    }

    @GetMapping("/{idReportCard}")
    public ResponseEntity<?> findById(@PathVariable(name = "idReportCard") int idReportCard) {
        try{
            ReportCardDTO reportCard = reportCardService.getReportCard(idReportCard);
            if(reportCard == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(reportCardService.getReportCard(idReportCard), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getByIdStudent/{idStudent}")
    public ResponseEntity<?> findByIdStudent(@PathVariable(name = "idStudent") int idStudent) {
        try{
            List<ReportCardDTO> reportCardDTOs = reportCardService.getReportCardByIdStudent(idStudent);
            if(reportCardDTOs == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(reportCardDTOs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
