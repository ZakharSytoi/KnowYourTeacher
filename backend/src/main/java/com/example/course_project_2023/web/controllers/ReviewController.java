package com.example.course_project_2023.web.controllers;

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

    @PostMapping("/{id:\\d+}/like")
    public ResponseEntity<?> likePost(@PathVariable Long id){
        return ResponseEntity.ok().body("post " + id + " liked");
    }
    @PostMapping("/{id:\\d+}/dislike")
    public ResponseEntity<?> dislikePost(@PathVariable Long id){
        return ResponseEntity.ok().body("post " + id + " disliked");
    }
}
