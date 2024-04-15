package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.ReviewService;
import com.example.course_project_2023.service.dto.DislikeOnReviewResponseDto;
import com.example.course_project_2023.service.dto.LikeOnReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v2/reviews")
public class LikingController {

    private final ReviewService reviewService;

    @PostMapping("/{id:\\d+}/like")
    public ResponseEntity<LikeOnReviewResponseDto> likeReview(@PathVariable Long id) {
        return ResponseEntity.ok().body(reviewService.likeReviewV2(id));
    }

    @DeleteMapping("/{id:\\d+}/unlike")
    public ResponseEntity<LikeOnReviewResponseDto> unLikeReview(@PathVariable Long id) {
        return ResponseEntity.ok().body(reviewService.unLikeReviewV2(id));
    }

    @PostMapping("/{id:\\d+}/dislike")
    public ResponseEntity<DislikeOnReviewResponseDto> dislikeReview(@PathVariable Long id) {
        return ResponseEntity.ok().body(reviewService.dislikeReviewV2(id));
    }

    @DeleteMapping("/{id:\\d+}/undislike")
    public ResponseEntity<DislikeOnReviewResponseDto> unDislikeReview(@PathVariable Long id) {
        return ResponseEntity.ok().body(reviewService.unDislikeReviewV2(id));
    }
}
