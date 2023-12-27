package com.example.course_project_2023.service.mappers;

import com.example.course_project_2023.repository.model.views.ReviewView;
import com.example.course_project_2023.service.util.LikingUtil;
import com.example.course_project_2023.service.dto.ReviewDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ReviewViewMapper {
    @Autowired
    LikingUtil likingUtil;
    @Mappings({
            @Mapping(target = "likeLink",
                    expression = """
                            java( org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath()
                                            .path("/knowyourteacher-api/v1/reviews/")
                                            .path(reviewView.getId().toString()).path("/like").toUriString(); )"""),
            @Mapping(target = "dislikeLink",
                    expression = """
                            java( org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath()
                                            .path("/knowyourteacher-api/v1/reviews/")
                                            .path(reviewView.getId().toString()).path("/dislike").toUriString(); )"""),
            @Mapping(target = "isLiked",
                    expression = "java( likingUtil.isLiked(userId, reviewView.getId()) )"),
            @Mapping(target = "isDisliked",
                    expression = "java( likingUtil.isDisliked(userId, reviewView.getId()); )")
    })
    public abstract ReviewDto reviewDtoFromReviewView(ReviewView reviewView, @Context Long userId);

}
