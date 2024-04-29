package com.example.course_project_2023.service.dto;

public record UserResponseDto(
    Long id,
    String nickname,
    String email,
    String fieldOfStudies,
    UniversityDto university
) {
}
