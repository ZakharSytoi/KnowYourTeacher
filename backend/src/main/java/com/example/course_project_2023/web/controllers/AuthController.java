package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.AuthService;
import com.example.course_project_2023.service.dto.JwtResponse;
import com.example.course_project_2023.service.dto.LoginUserDtoRequest;
import com.example.course_project_2023.service.dto.UserRegistrationDtoRequest;
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
    public ResponseEntity<?> login(@RequestBody LoginUserDtoRequest loginUserDto) {
        return ResponseEntity.ok(new JwtResponse(authService.authenticateUser(loginUserDto)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDtoRequest userDtoRequest) throws UserWithEmailAlreadyExistsException, UserWithNicknameAlreadyExistsException {
        authService.registerUser(userDtoRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
    }

    @ExceptionHandler({UserWithEmailAlreadyExistsException.class})
    public ResponseEntity<?> handleUserWithEmailAlreadyExistsException(UserWithEmailAlreadyExistsException exception){
        return ResponseEntity.status(409).body(exception.getMessage());
    }

    @ExceptionHandler({UserWithNicknameAlreadyExistsException.class})
    public ResponseEntity<?> handleUserWithNicknameAlreadyExistsException(UserWithEmailAlreadyExistsException exception){
        return ResponseEntity.status(409).body(exception.getMessage());
    }

}
