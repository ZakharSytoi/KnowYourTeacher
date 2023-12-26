package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.ReviewService;
import com.example.course_project_2023.service.TeacherService;
import com.example.course_project_2023.service.TeacherWithMostPopularReviewService;
import com.example.course_project_2023.service.dto.ReviewDto;
import com.example.course_project_2023.service.dto.TeacherCardDto;
import com.example.course_project_2023.service.dto.TeacherPreviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1/teachers")
public class TeacherController {
    private final TeacherWithMostPopularReviewService teacherWithMostPopularReviewService;
    private final TeacherService teacherService;
    private final ReviewService reviewService;

    @GetMapping("/topten")
    public ResponseEntity<List<TeacherPreviewDto>> getTopTenTeachers(
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(teacherWithMostPopularReviewService.getNTeachersWithMostPopularReviews(pageSize));
    }

    @GetMapping(value = "/pictures/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImg(@PathVariable String id) {
        return teacherService.getTeacherPictureByPictureId(id);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<TeacherCardDto> getTeacherById(@PathVariable Long id){
        return ResponseEntity.ok().body(teacherService.getTeacherById(id));
    }

    @GetMapping("/{id:\\d+}/reviews")
    public ResponseEntity<List<ReviewDto>> getTeacherReviews(@PathVariable Long id){
        return ResponseEntity.ok().body(reviewService.findAllByTeachersId(id));
    }
}
