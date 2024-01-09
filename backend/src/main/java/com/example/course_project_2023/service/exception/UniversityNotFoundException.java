package com.example.course_project_2023.service.exception;

public class UniversityNotFoundException extends RuntimeException{
    public UniversityNotFoundException(String message) {
        super(message);
    }
}
