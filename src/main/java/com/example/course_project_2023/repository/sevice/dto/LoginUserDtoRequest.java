package com.example.course_project_2023.repository.sevice.dto;

import lombok.Data;

@Data
public class LoginUserDtoRequest {
    private String username;
    private String password;
}
