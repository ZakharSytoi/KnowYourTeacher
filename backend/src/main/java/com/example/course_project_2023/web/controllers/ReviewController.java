package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{id:\\d+}/like")
    public ResponseEntity<?> likeReview(@PathVariable Long id){
        reviewService.likeReview(id);
        return ResponseEntity.ok().body("post " + id + " liked");
    }
    @PostMapping("/{id:\\d+}/dislike")
    public ResponseEntity<?> dislikeReview(@PathVariable Long id){
        reviewService.dislikeReview(id);
        return ResponseEntity.ok().body("post " + id + " disliked");
    }
}
