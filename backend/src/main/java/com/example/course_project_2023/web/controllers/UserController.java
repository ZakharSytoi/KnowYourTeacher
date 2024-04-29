package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.UserService;
import com.example.course_project_2023.service.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1/user")
public class UserController {

    private final UserService userService;
    @GetMapping()
    ResponseEntity<UserResponseDto> getUserById(){
        return ResponseEntity.ok(userService.getUserInfo());
    }
}
