package com.example.course_project_2023.service.mappers;

import com.example.course_project_2023.repository.model.views.TeacherWithMostPopularReview;
import com.example.course_project_2023.service.dto.TeacherPreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeacherWithMostPopularReviewMapper {
    TeacherWithMostPopularReviewMapper INSTANCE = Mappers.getMapper(TeacherWithMostPopularReviewMapper.class);

    List<TeacherPreviewDto> teacherListToTeacherDtoResponseList(List<TeacherWithMostPopularReview> teachers);

    @Mapping(target = "teacherPictureUri",
            source = "profileImageLink")
    TeacherPreviewDto teacherToTeacherDtoResponse(TeacherWithMostPopularReview teacher);
}
