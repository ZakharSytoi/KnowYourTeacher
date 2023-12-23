package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.dto.ShortTeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1/teachers")
public class TeacherController {
    @GetMapping("/topten")
    public ResponseEntity<List<ShortTeacherDto>> getTopTenTeachers(){
        return null;
    }
}
