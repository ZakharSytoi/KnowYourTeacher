package com.example.course_project_2023.service.exception.notFound;

public class ProfileNotFoundException extends EntityNotFoundException{
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
