package com.example.course_project_2023.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.course_project_2023.repository.daos.TeacherRepository;
import com.example.course_project_2023.repository.daos.UniversityRepository;
import com.example.course_project_2023.repository.daos.UserRepository;
import com.example.course_project_2023.repository.model.Teacher;
import com.example.course_project_2023.service.dto.TeacherCardDto;
import com.example.course_project_2023.service.exception.notFound.TeacherNotFoundException;
import com.example.course_project_2023.service.exception.notFound.UniversityNotFoundException;
import com.example.course_project_2023.service.mappers.TeacherMapper;
import com.example.course_project_2023.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherService {
    @Value("${aws.s3.publicBucketName}")
    private String publicBucketName;
    private final TeacherRepository teacherRepository;
    private final UniversityRepository universityRepository;
    private final UserUtil userSecurityUtil;
    private final UserRepository userRepository;
    private final TeacherMapper teacherMapper = TeacherMapper.INSTANCE;
    private final AmazonS3 s3Client;

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
                () -> new UniversityNotFoundException(String.format("University with id %s not found", universityId)))
        );
        teacher.setUser(userRepository.findById(userId).get());

        UUID uuid = UUID.randomUUID();

        String objectKey = uuid.toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(photo.getContentType());
        metadata.setContentLength(photo.getSize());

        PutObjectRequest request = new PutObjectRequest(
                publicBucketName,
                objectKey,
                photo.getInputStream(),
                metadata
        );

        s3Client.putObject(request);

        teacher.setProfileImageLink("https://kyt-public.s3.eu-central-1.amazonaws.com/" + objectKey);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/knowyourteacher-api/v1/teachers/")
                .path(teacherRepository.save(teacher).getId().toString())
                .build().toUri();
    }
}
