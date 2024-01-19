package com.example.course_project_2023.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRegisterDtoRequest(
        @NotNull
        @Length(min = 6, max = 30)
        String nickname,
        @NotNull
        @Min(0)
        @Max(10000)
        Long universityId,
        @NotNull
        @Length(min = 3, max = 50)
        String fieldOfStudies,
        @NotNull
        @Email
        @Length(max = 60)
        String email,
        @NotNull
        @Length(min = 8, max = 30)
        String password
) {
}
