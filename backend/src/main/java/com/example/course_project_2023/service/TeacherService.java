package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.PictureRepository;
import com.example.course_project_2023.repository.daos.TeacherRepository;
import com.example.course_project_2023.service.dto.TeacherCardDto;
import com.example.course_project_2023.service.exception.PictureNotFoundException;
import com.example.course_project_2023.service.exception.TeacherNotFoundException;
import com.example.course_project_2023.service.mappers.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final PictureRepository pictureRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper = TeacherMapper.INSTANCE;

    public byte[] getTeacherPictureByPictureId(String uuid) {
        return pictureRepository.findPictureById(uuid).orElseThrow(() ->
                new PictureNotFoundException(String.format("Picture not found. Picture id was %s", uuid))
        ).getData();
    }

    public TeacherCardDto getTeacherById(Long id) {
        return teacherMapper.cardDtoFromTeacher(teacherRepository.findById(id).orElseThrow(
                () -> new TeacherNotFoundException(String.format("Teacher with id %s not found", id))
        ));
    }
}
