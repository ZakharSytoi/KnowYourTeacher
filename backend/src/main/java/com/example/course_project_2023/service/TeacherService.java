package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.PictureRepository;
import com.example.course_project_2023.service.exception.PictureNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final PictureRepository pictureRepository;

    public byte[] getTeacherPictureByPictureId(String uuid) {
        return pictureRepository.findPictureById(uuid).orElseThrow(() ->
                new PictureNotFoundException(String.format("Picture not found. Picture id was %s", uuid))
        ).getData();
    }
}
