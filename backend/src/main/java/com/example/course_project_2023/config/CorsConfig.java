package com.example.course_project_2023.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowCredentials(true);
        registry.addMapping("/**").allowedOrigins("http://localhost:8077").allowCredentials(true);
        registry.addMapping("/**").allowedOrigins("http://localhost:80").allowCredentials(true);
    }
}