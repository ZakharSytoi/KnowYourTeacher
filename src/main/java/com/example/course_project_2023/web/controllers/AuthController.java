package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.sevice.AuthService;
import com.example.course_project_2023.sevice.dto.JwtResponse;
import com.example.course_project_2023.sevice.dto.LoginUserDtoRequest;
import com.example.course_project_2023.sevice.dto.UserRegistrationDtoRequest;
import com.example.course_project_2023.sevice.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDtoRequest loginUserDto){
        return ResponseEntity.ok(new JwtResponse(authService.authenticateUser(loginUserDto)));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDtoRequest userDtoRequest) throws UserAlreadyExistsException {
        authService.registerUser(userDtoRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
    }

}
