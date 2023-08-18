package com.example.projectfinal.service.implement;

import com.cloudinary.Cloudinary;
import com.example.projectfinal.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadImple implements FileUploadService {
    private final Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile multipartFile, String name) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", name))
                .get("url")
                .toString();
    }
}
