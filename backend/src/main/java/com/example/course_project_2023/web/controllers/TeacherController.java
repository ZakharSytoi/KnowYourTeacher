package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.TeacherWithMostPopularReviewService;
import com.example.course_project_2023.service.dto.TeacherWithMostPopularReviewDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1/teachers")
public class TeacherController {
    private final TeacherWithMostPopularReviewService teacherWithMostPopularReviewService;
    @GetMapping("/topten")
    public ResponseEntity<List<TeacherWithMostPopularReviewDtoResponse>> getTopTenTeachers(
            @RequestParam(required = false, defaultValue = "10") int pageSize){
        return ResponseEntity.ok().body(teacherWithMostPopularReviewService.getNTeachersWithMostPopularReviews(pageSize));
    }
}
