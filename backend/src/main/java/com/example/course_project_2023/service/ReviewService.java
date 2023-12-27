package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.ReviewViewRepository;
import com.example.course_project_2023.service.dto.ReviewDto;
import com.example.course_project_2023.service.mappers.ReviewViewMapper;
import com.example.course_project_2023.service.util.LikingUtil;
import com.example.course_project_2023.service.util.UserSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewViewRepository reviewViewRepository;
    private final ReviewViewMapper reviewViewMapper;
    private final UserSecurityUtil userSecurityUtil;
    private final LikingUtil likingUtil;

    public Page<ReviewDto> findAllByTeachersId(Long reviewId, int pageNumber, int pageSize) {
        Long userId = userSecurityUtil.getUserIdFromContext();
        return reviewViewRepository.findReviewsByTeacherId(reviewId, PageRequest.of(pageNumber, pageSize))
                .map((ent) -> reviewViewMapper.reviewDtoFromReviewView(ent, userId));
    }

    public void dislikeReview(Long reviewId) {
        likingUtil.dislike(userSecurityUtil.getUserIdFromContext(), reviewId);
    }

    public void likeReview(Long reviewId) {
        likingUtil.like(userSecurityUtil.getUserIdFromContext(), reviewId);
    }

    public void unlikeReview(Long reviewId) {
        likingUtil.unlike(userSecurityUtil.getUserIdFromContext(), reviewId);
    }

    public void undislikeReview(Long reviewId) {
        likingUtil.undislike(userSecurityUtil.getUserIdFromContext(), reviewId);
    }

}
