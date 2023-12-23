package com.example.course_project_2023.service.exception;

public class UserWithNicknameAlreadyExistsException extends Exception {
    public UserWithNicknameAlreadyExistsException(String message) {
        super(message);
    }

}
