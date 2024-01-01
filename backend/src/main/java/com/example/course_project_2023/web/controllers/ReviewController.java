package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.ReviewService;
import com.example.course_project_2023.service.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping()
    public ResponseEntity<Page<ReviewDto>> getTeacherReviews(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam Map<String, String> searchParams) {
        return ResponseEntity.ok().body(reviewService.findBySearchParams(pageNumber, pageSize, searchParams));
    }

    @PostMapping("/{id:\\d+}/like")
    public ResponseEntity<?> likeReview(@PathVariable Long id) {
        reviewService.likeReview(id);
        return ResponseEntity.ok().body("post " + id + " liked");
    }

    @PostMapping("/{id:\\d+}/dislike")
    public ResponseEntity<?> dislikeReview(@PathVariable Long id) {
        reviewService.dislikeReview(id);
        return ResponseEntity.ok().body("post " + id + " disliked");
    }
}
