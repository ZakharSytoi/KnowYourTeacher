package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.repository.daos.TeacherWithMostPopularReviewRepository;
import com.example.course_project_2023.repository.model.views.TeacherWithMostPopularReview;
import com.example.course_project_2023.service.TeacherService;
import com.example.course_project_2023.service.TeacherWithMostPopularReviewService;
import com.example.course_project_2023.service.dto.TeacherWithMostPopularReviewDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @GetMapping("/topten")
    public ResponseEntity<List<TeacherWithMostPopularReviewDtoResponse>> getTopTenTeachers(
            @RequestParam(required = false, defaultValue = "10") int pageSize){
        return ResponseEntity.ok().body(teacherWithMostPopularReviewService.getNTeachersWithMostPopularReviews(pageSize));
    }

    @GetMapping(value = "/pictures/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public@ResponseBody byte[] getImg(@PathVariable String id){
        return teacherService.getTeacherPictureByPictureId(id);
    }
}
