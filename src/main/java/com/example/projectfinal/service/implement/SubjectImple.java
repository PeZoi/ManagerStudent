package com.example.projectfinal.service.implement;

import com.example.projectfinal.dto.SubjectDTO;
import com.example.projectfinal.entity.*;
import com.example.projectfinal.repository.*;
import com.example.projectfinal.service.SubjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectImple implements SubjectService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    private SubjectRepository subjectRepository;
    private ReportCardRepository reportCardRepository;
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ReportCardDetailRepository reportCardDetailRepository;

    @Autowired
    public SubjectImple(SubjectRepository subjectRepository, ReportCardRepository reportCardRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.reportCardRepository = reportCardRepository;
        this.teacherRepository = teacherRepository;
    }


    @Override
    public SubjectDTO getSubject(int idSubject) {
        Optional<Subject> subject = subjectRepository.findById(idSubject);
        if (subject.isEmpty()) {
            return null;
        } else {
            SubjectDTO subjectDTO = modelMapper.map(subject.get(), SubjectDTO.class);
            return subjectDTO;
        }
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectDTO> SubjectDTOs = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectDTO subjectDTO = modelMapper.map(subject, SubjectDTO.class);
            SubjectDTOs.add(subjectDTO);
        }
        return SubjectDTOs;
    }

    @Override
    @Transactional
    public SubjectDTO saveSubject(String subject) {
        try {
            // Chuyển subject (JSON) về dạng object java
            Subject subject_old = objectMapper.readValue(subject, Subject.class);
            // Lưu object đó xuống database
            Subject subject_new = subjectRepository.save(subject_old);

            // Khi thêm subject mới thì trong bảng điểm của tất cả sinh viên cũng phải có môn học đó
            List<Student> studentList = studentRepository.findAll();
            for (Student student : studentList) {
                ReportCard reportCard = new ReportCard();
                reportCard.setStudent(student);
                reportCard.setSubject(subject_new);
                ReportCard reportCardSave = reportCardRepository.save(reportCard);
                // Khi có report card rồi thì phải có điểm mặc định
                for (int i = 1; i <= 6; i++) {
                    ReportCardDetail reportCardDetail = new ReportCardDetail();
                    if (i == 1) {
                        reportCardDetail.setNameReport("15");
                        reportCardDetail.setSemester("HK1");
                    } else if (i == 2) {
                        reportCardDetail.setNameReport("45");
                        reportCardDetail.setSemester("HK1");
                    } else if (i == 3) {
                        reportCardDetail.setNameReport("Final");
                        reportCardDetail.setSemester("HK1");
                    } else if (i == 4) {
                        reportCardDetail.setNameReport("15");
                        reportCardDetail.setSemester("HK2");
                    } else if (i == 5) {
                        reportCardDetail.setNameReport("45");
                        reportCardDetail.setSemester("HK2");
                    } else {
                        reportCardDetail.setNameReport("Final");
                        reportCardDetail.setSemester("HK2");
                    }
                    reportCardDetail.setReportCard(reportCardSave);
                    reportCardDetailRepository.save(reportCardDetail);
                }
            }


            // Rồi chuyển object đó thành object DTO để làm thành json đẩy lên client
            SubjectDTO subjectDTO = modelMapper.map(subject_new, SubjectDTO.class);
            return subjectDTO;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    @Transactional
    public SubjectDTO updateSubject(String subject) {
        try {
            // Chuyển subject (JSON) về dạng object java
            Subject subject_old = objectMapper.readValue(subject, Subject.class);
            // Lưu object đó xuống database
            Subject subject_new = subjectRepository.save(subject_old);
            // Rồi chuyển object đó thành object DTO để làm thành json đẩy lên client
            SubjectDTO subjectDTO = modelMapper.map(subject_new, SubjectDTO.class);
            return subjectDTO;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteSubject(int idSubject) {
        Optional<Subject> subjectOptional = subjectRepository.findById(idSubject);

        if (subjectOptional.isPresent()) {
            Subject subject = subjectOptional.get();

            // Xoá Report Card có liên quan đến subject
            List<ReportCard> reportCards = reportCardRepository.findBySubject(subject);
            for (ReportCard reportCard : reportCards) {
                reportCard.setSubject(null);
                reportCardRepository.delete(reportCard);
            }

            // Set Teacher có liên quan đến subject
            List<Teacher> teachers = subject.getTeachers();
            for (Teacher teacher : teachers) {
                List<Subject> subjectList = teacher.getSubjects();
                subjectList.remove(subject);
                teacher.setSubjects(subjectList);
                teacherRepository.saveAndFlush(teacher);
            }

            subjectRepository.delete(subject);
        }

    }
}
