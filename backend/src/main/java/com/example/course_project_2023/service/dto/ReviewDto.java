package com.example.course_project_2023.service.dto;

import java.util.Date;

public record ReviewDto(
        Long id,
        Short score,
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
