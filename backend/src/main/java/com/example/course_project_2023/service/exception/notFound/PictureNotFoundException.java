package com.example.course_project_2023.service.exception.notFound;

public class PictureNotFoundException extends EntityNotFoundException{
    public PictureNotFoundException(String message) {
        super(message);
    }
}
