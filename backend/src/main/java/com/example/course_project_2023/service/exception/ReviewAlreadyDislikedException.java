package com.example.course_project_2023.service.exception;

public class ReviewAlreadyDislikedException extends RuntimeException{
    public ReviewAlreadyDislikedException(String message) {
        super(message);
    }
}
