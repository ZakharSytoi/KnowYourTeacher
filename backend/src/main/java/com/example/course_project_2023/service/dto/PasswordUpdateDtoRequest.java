package com.example.course_project_2023.service.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record PasswordUpdateDtoRequest(

        @NotNull(message = "password can not be null")
        String currentPassword,
        @NotNull(message = "password can not be null")
        @Length(min = 8, max = 30, message = "password length must be between 8 and 30 characters")
        String newPassword
) {
}
