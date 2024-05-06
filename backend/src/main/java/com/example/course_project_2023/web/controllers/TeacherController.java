package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.ReviewService;
import com.example.course_project_2023.service.TeacherService;
import com.example.course_project_2023.service.TeacherWithMostPopularReviewService;
import com.example.course_project_2023.service.dto.ReviewDto;
import com.example.course_project_2023.service.dto.ShortReviewDto;
import com.example.course_project_2023.service.dto.TeacherCardDto;
import com.example.course_project_2023.service.dto.TeacherPreviewDto;
import com.example.course_project_2023.service.vlidation.ImageResolution;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<TeacherCardDto> getTeacherById(@PathVariable Long id) {
        //TimeUnit.SECONDS.sleep(5);
        return ResponseEntity.ok().body(teacherService.getTeacherById(id));
    }

    @GetMapping("/{teacherId:\\d+}/reviews")
    public ResponseEntity<Page<ReviewDto>> getTeacherReviews(
            @PathVariable Long teacherId,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(reviewService.findAllByTeachersId(teacherId, pageNumber, pageSize));
    }

    @GetMapping("/{teacherId:\\d+}/review")
    public ResponseEntity<ShortReviewDto> getUserReviewByTeacherId(@PathVariable Long teacherId) {
        return ResponseEntity.ok().body(reviewService.getUserReviewByTeacherId(teacherId));
    }

    @GetMapping()
    public ResponseEntity<Page<TeacherPreviewDto>> findTeachersByParams(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam Map<String, String> searchParams) {
        return ResponseEntity.ok().body(teacherWithMostPopularReviewService.findBySearchParams(pageNumber, pageSize, searchParams));
    }

    @PostMapping("/create")
    @Valid
    public ResponseEntity<?> createTeacher(
            @Length(min = 2, max = 50, message = "name length must be between 2 and 50 characters")
            @RequestParam("name") String name,
            @Length(min = 2, max = 50, message = "surname length must be between 2 and 50 characters")
            @RequestParam("surname") String surname,
            @Min(value = 0, message = "universityId value must be greater then or equal 0")
            @Max(value = 10000, message = "universityId value must not exceed 10000")
            @RequestParam("universityId") Long universityId,
            @ImageResolution(minWidth = 100, minHeight = 100, maxWidth = 1000, maxHeight = 1000)
            @RequestParam("image") MultipartFile photo
    ) throws IOException {
        URI uri = teacherService.createTeacher(name, surname, universityId, photo);
        System.out.println(uri);
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{teacherId:\\d+}/review")
    public ResponseEntity<?> postUserReviewByTeacherId(@PathVariable Long teacherId,
                                                       @RequestBody @Valid ShortReviewDto reviewDto) {
        return ResponseEntity.created(reviewService.createUserReviewByTeacherId(teacherId, reviewDto)).build();
    }

    @DeleteMapping("/{teacherId:\\d+}/review")
    public ResponseEntity<?> deleteUserReviewByTeacherId(@PathVariable Long teacherId) {
        reviewService.deleteUserReviewByTeacherId(teacherId);
        return ResponseEntity.noContent().build();
    }


}
