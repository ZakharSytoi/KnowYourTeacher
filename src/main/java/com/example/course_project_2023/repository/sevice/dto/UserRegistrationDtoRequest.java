package com.example.course_project_2023.repository.sevice.dto;

public record UserRegistrationDtoRequest(String nickname,
                                         Long universityId,
                                         String fieldOfStudies,
                                         String email,
                                         String password,
                                         RequestedRole role
) {
}
