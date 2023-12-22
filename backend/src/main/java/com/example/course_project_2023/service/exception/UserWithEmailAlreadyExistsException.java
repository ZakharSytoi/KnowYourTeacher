package com.example.course_project_2023.service.exception;

public class UserWithEmailAlreadyExistsException extends Throwable {
    public UserWithEmailAlreadyExistsException(String message) {
        super(message);
    }

}
