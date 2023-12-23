package com.example.course_project_2023.service.mappers;

import com.example.course_project_2023.repository.model.views.TeacherWithMostPopularReview;
import com.example.course_project_2023.service.dto.TeacherWithMostPopularReviewDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface TeacherWithMostPopularReviewMapper {
    TeacherWithMostPopularReviewMapper INSTANCE = Mappers.getMapper(TeacherWithMostPopularReviewMapper.class);

    List<TeacherWithMostPopularReviewDtoResponse> teacherToTeacherDtoResponse(List<TeacherWithMostPopularReview> teachers);
}
