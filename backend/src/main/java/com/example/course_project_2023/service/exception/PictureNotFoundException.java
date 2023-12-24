package com.example.course_project_2023.service.exception;

public class PictureNotFoundException extends RuntimeException{
    public PictureNotFoundException(String message) {
        super(message);
    }
}
