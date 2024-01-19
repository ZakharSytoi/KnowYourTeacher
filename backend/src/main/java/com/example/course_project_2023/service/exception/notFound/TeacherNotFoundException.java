package com.example.course_project_2023.service.exception.notFound;

public class TeacherNotFoundException extends EntityNotFoundException{
    public TeacherNotFoundException(String message) {
        super(message);
    }
}
