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
            expression = "java( org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath()\n" +
                    "                .path(\"/knowyourteacher-api/v1/teachers/pictures/\")\n" +
                    "                .path(teacher.getTeacherPictureId())\n" +
                    "                .toUriString(); )")
    TeacherPreviewDto teacherToTeacherDtoResponse(TeacherWithMostPopularReview teacher);
}
