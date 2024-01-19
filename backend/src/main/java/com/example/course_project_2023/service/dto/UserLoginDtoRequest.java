package com.example.course_project_2023.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserLoginDtoRequest {
    @NotNull
    @Email
    @Length(max = 30)
    private String username;
    @NotNull
    @Email
    @Length(max = 30)
    private String password;
}
