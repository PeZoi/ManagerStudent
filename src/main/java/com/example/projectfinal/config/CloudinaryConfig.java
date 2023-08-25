package com.example.projectfinal.config;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "dz8vpmcub";
    private final String API_KEY = "159899294781247";
    private final String API_SECRET = "A8bobjl4f7Dip-mG1gYsU3SDvE4";

    //    Config cloudinary (Nơi để chứa ảnh)
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);
    }

    //    Config model mapper (để chuyển từ entity sang dto dạng json)
    //    Đỡ phải viết thêm các entity
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
