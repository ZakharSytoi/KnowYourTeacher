package com.example.course_project_2023.service.dto;

public record TeacherPreviewDto(
        Long id,
        String teacherName,
        String teacherSurname,
        String universityName,
        Double avgScore,
        String mostPopularReviewText,
        String teacherPictureUri
) {}
