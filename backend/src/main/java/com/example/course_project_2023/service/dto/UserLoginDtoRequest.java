package com.example.course_project_2023.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserLoginDtoRequest {
    @NotNull(message = "email can not be null")
    @Email(message = "email value must be a valid email address")
    @Length(max = 30)
    private String username;
    @NotNull(message = "password can not be null")
    @Length(max = 30, message =  "password length must not exceed 30 characters")
    private String password;
}
