package com.example.course_project_2023.web.controllers;

import com.example.course_project_2023.service.UserService;
import com.example.course_project_2023.service.dto.UserInfoUpdateDtoRequest;
import com.example.course_project_2023.service.dto.PasswordUpdateDtoRequest;
import com.example.course_project_2023.service.dto.SearchedReviewDto;
import com.example.course_project_2023.service.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1/user")
public class UserController {

    private final UserService userService;
    @GetMapping()
    ResponseEntity<UserResponseDto> getUserById(){
        return ResponseEntity.ok(userService.getUserInfo());
    }

    @GetMapping("/reviews")
    public ResponseEntity<Page<SearchedReviewDto>> getTeacherReviews(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(userService.findAllUserReviews(pageNumber, pageSize));
    }

    @PostMapping ("/password")
    ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordUpdateDtoRequest passwordUpdateDtoRequest){
        userService.updateUserPassword(passwordUpdateDtoRequest);
        return ResponseEntity.ok("Password updated successfully");
    }

    @PutMapping ()
    ResponseEntity<String> updateUserInfo(@Valid @RequestBody UserInfoUpdateDtoRequest userInfoUpdateDtoRequest){
        userService.updateUserInfo(userInfoUpdateDtoRequest);
        return ResponseEntity.ok("User info updated successfully");
    }


}
