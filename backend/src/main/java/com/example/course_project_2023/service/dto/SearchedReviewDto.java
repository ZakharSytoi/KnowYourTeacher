package com.example.course_project_2023.service.dto;

import java.util.Date;

public record SearchedReviewDto(
        Long id,
        String teacherName,
        String teacherSurname,
        Long teacherId,
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
