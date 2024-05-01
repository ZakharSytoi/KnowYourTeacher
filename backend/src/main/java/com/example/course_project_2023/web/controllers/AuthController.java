package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.AuthService;
import com.example.course_project_2023.service.dto.JwtResponse;
import com.example.course_project_2023.service.dto.UserLoginDtoRequest;
import com.example.course_project_2023.service.dto.UserRegisterDtoRequest;
import com.example.course_project_2023.service.exception.UserWithEmailAlreadyExistsException;
import com.example.course_project_2023.service.exception.UserWithNicknameAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1")
@Validated
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDtoRequest loginUserDto) {
        return ResponseEntity.ok(new JwtResponse(authService.authenticateUser(loginUserDto)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDtoRequest userDtoRequest) throws UserWithEmailAlreadyExistsException, UserWithNicknameAlreadyExistsException {
        authService.registerUser(userDtoRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
    }
}
