package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.UniversityService;
import com.example.course_project_2023.service.dto.UniversityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1/universities")
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping()
    public ResponseEntity<List<UniversityDto>> getAllUniversities() {
        return new ResponseEntity<>(universityService.getAllUniversities(), HttpStatus.OK);
    }
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<UniversityDto> getUniversityById(@PathVariable Long id) {
        return new ResponseEntity<>(universityService.getUniversityById(id), HttpStatus.OK);
    }
}
