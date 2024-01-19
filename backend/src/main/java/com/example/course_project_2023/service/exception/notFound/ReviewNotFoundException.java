package com.example.course_project_2023.service.exception.notFound;

public class ReviewNotFoundException extends EntityNotFoundException{
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
