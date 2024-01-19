package com.example.course_project_2023.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRegisterDtoRequest(
        @NotNull(message = "Nickname can not be Null")
        @Length(min = 6, max = 30, message = "nickname length must be between 6 and 30 characters")
        String nickname,
        @NotNull(message = "UniversityId can not be Null")
        @Min(value = 0, message = "universityId value must be greater then or equal 0")
        @Max(value = 10000, message = "universityId value must not exceed 10000")
        Long universityId,
        @NotNull(message = "fieldOdStudies can not be null")
        @Length(min = 3, max = 50, message = "fieldOfStudies length must be between 6 and 30 characters")
        String fieldOfStudies,
        @NotNull(message = "email can not be null")
        @Email(message = "email value must be a valid email address")
        @Length(max = 60, message = "email length must not exceed 60 characters")
        String email,
        @NotNull(message = "password can not be null")
        @Length(min = 8, max = 30, message = "password length must be between 8 and 30 characters")
        String password
) {
}
