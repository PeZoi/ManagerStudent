package com.example.projectfinal.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    String uploadFile(MultipartFile multipartFile, String name) throws IOException;
}
