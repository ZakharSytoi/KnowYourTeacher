package com.example.course_project_2023.service.dto;

import java.util.Date;

public record ReviewDto(
        Long id,
        Short score,
        String subjectName,
        String reviewText,
        Date createDate,
        String likeLink,
        String dislikeLink,
        boolean isLiked,
        boolean isDisliked
) {

}
