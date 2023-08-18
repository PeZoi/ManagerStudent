package com.example.projectfinal.repository;

import com.example.projectfinal.entity.ReportCard;
import com.example.projectfinal.entity.ReportCardDetail;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportCardRepository extends JpaRepository<ReportCard, Integer> {
    public List<ReportCard> findBySubject(Subject subject);
    public List<ReportCard> findByStudent(Student student);
    public List<ReportCard> findAllBySubjectIsNull();
}
