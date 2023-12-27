package com.example.course_project_2023.service.dto;

public record TeacherCardDto(
        Long id,
        String name,
        String surname,
        String universityName,
        Double avgScore,
        String teacherPictureUri,
        String teacherReviewsUri
) {
}
