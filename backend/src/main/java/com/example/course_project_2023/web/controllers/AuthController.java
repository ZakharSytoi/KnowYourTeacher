package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.AuthService;
import com.example.course_project_2023.service.dto.JwtResponse;
import com.example.course_project_2023.service.dto.UserLoginDtoRequest;
import com.example.course_project_2023.service.dto.UserRegisterDtoRequest;
import com.example.course_project_2023.service.exception.UserWithEmailAlreadyExistsException;
import com.example.course_project_2023.service.exception.UserWithNicknameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDtoRequest loginUserDto) {
        return ResponseEntity.ok(new JwtResponse(authService.authenticateUser(loginUserDto)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDtoRequest userDtoRequest) throws UserWithEmailAlreadyExistsException, UserWithNicknameAlreadyExistsException {
        authService.registerUser(userDtoRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
    }

    @ExceptionHandler({UserWithEmailAlreadyExistsException.class, UserWithNicknameAlreadyExistsException.class})
    public ResponseEntity<?> handleUserWithEmailAlreadyExistsException(Exception exception){
        return ResponseEntity.status(409).body(exception.getMessage());
    }

}
