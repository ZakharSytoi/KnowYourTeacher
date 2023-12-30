package com.example.course_project_2023.service.mappers;

import com.example.course_project_2023.repository.model.Review;
import com.example.course_project_2023.service.dto.ShortReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    ShortReviewDto getShortReviewDtoFromReview(Review review);
}
