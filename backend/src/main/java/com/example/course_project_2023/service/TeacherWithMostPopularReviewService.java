package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.TeacherWithMostPopularReviewRepository;
import com.example.course_project_2023.service.dto.TeacherPreviewDto;
import com.example.course_project_2023.service.mappers.TeacherWithMostPopularReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherWithMostPopularReviewService {

    private final TeacherWithMostPopularReviewRepository teacherRepository;
    private final TeacherWithMostPopularReviewMapper teacherMapper = TeacherWithMostPopularReviewMapper.INSTANCE;

    public List<TeacherPreviewDto> getNTeachersWithMostPopularReviews(int n){
        return teacherMapper.teacherListToTeacherDtoResponseList(teacherRepository.findAll(PageRequest.of(0, 10)).getContent());
    }
}
