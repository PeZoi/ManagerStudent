package com.example.projectfinal.repository;

import com.example.projectfinal.entity.ReportCardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCardDetailRepository extends JpaRepository<ReportCardDetail, Integer> {

}
