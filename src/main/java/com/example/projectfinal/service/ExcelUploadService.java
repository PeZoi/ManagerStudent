package com.example.projectfinal.service;

import com.example.projectfinal.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ExcelUploadService {
    public boolean isValidExcelFile(MultipartFile file);
    public List<Student> getStudentsDataFromExcel(InputStream inputStream);
}
