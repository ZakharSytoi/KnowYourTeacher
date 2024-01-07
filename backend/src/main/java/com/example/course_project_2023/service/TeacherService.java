package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.PictureRepository;
import com.example.course_project_2023.repository.daos.TeacherRepository;
import com.example.course_project_2023.repository.daos.UniversityRepository;
import com.example.course_project_2023.repository.daos.UserRepository;
import com.example.course_project_2023.repository.model.Picture;
import com.example.course_project_2023.repository.model.Teacher;
import com.example.course_project_2023.service.dto.TeacherCardDto;
import com.example.course_project_2023.service.exception.PictureNotFoundException;
import com.example.course_project_2023.service.exception.TeacherNotFoundException;
import com.example.course_project_2023.service.exception.UniversityNotFoundException;
import com.example.course_project_2023.service.mappers.TeacherMapper;
import com.example.course_project_2023.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final PictureRepository pictureRepository;
    private final TeacherRepository teacherRepository;
    private final UniversityRepository universityRepository;
    private final UserUtil userSecurityUtil;
    private final UserRepository userRepository;
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

    public URI createTeacher(String name, String surname, Long universityId, MultipartFile photo) throws IOException {
        Long userId = userSecurityUtil.getUserIdFromContext();
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setUniversity(universityRepository.findById(universityId).orElseThrow(
                ()->new UniversityNotFoundException(String.format("University with id %s not found", universityId)))
        );
        teacher.setUser(userRepository.findById(userId).get());
        Picture picture = new Picture();
        picture.setName(photo.getName());
        picture.setType("png");
        picture.setData(photo.getBytes());
        teacher.setPicture(picture);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/knowyourteacher-api/v1/teachers/")
                .path(teacherRepository.save(teacher).getId().toString())
                .build().toUri();
    }
}
