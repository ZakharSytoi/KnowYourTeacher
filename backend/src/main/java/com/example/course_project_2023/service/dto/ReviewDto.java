package com.example.course_project_2023.service.dto;

import jakarta.persistence.Column;

import java.util.Date;

public record ReviewDto(
        Long id,
        Short score,
        String nickname,
        String universityName,
        String subjectName,
        String reviewText,
        Date createdDate,
        Long likeCount,
        Long dislikeCount,
        String likeLink,
        String dislikeLink,
        Boolean isLiked,
        Boolean isDisliked
) {

}
