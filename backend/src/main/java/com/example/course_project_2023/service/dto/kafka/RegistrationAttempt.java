package com.example.course_project_2023.service.dto.kafka;

public record RegistrationAttempt(
        String nickname,
        String email,
        String id
) {
}
