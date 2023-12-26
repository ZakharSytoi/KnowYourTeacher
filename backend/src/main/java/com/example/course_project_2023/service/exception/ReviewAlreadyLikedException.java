package com.example.course_project_2023.service.exception;

public class ReviewAlreadyLikedException extends RuntimeException{
    public ReviewAlreadyLikedException(String message) {
        super(message);
    }
}
