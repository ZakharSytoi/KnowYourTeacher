package com.example.course_project_2023.service.mappers;

import com.example.course_project_2023.repository.model.views.ReviewView;
import com.example.course_project_2023.service.dto.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReviewViewMapper {
    ReviewViewMapper INSTANCE = Mappers.getMapper(ReviewViewMapper.class);
    @Mappings({
            @Mapping(target = "likeLink",
                    expression = "java( org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath()\n" +
                            "                .path(\"/knowyourteacher-api/v1/reviews/\")\n" +
                            "                .path(reviewView.getId().toString()).path(\"/like\").toUriString(); )"),
            @Mapping(target = "dislikeLink",
                    expression = "java( org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath()\n" +
                            "                .path(\"/knowyourteacher-api/v1/reviews/\")\n" +
                            "                .path(reviewView.getId().toString()).path(\"/dislike\").toUriString(); )")
    })
    ReviewDto reviewDtoFromReviewView(ReviewView reviewView);
    List<ReviewDto> reviewDtoListFromReviewViewList(List<ReviewView> reviewView);
}
