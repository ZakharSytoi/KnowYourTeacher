package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.ReviewRepository;
import com.example.course_project_2023.repository.daos.ReviewViewRepository;
import com.example.course_project_2023.repository.daos.TeacherRepository;
import com.example.course_project_2023.repository.daos.UserRepository;
import com.example.course_project_2023.repository.model.Review;
import com.example.course_project_2023.service.dto.*;
import com.example.course_project_2023.service.exception.likedislike.ReviewAlreadyDislikedException;
import com.example.course_project_2023.service.exception.likedislike.ReviewAlreadyLikedException;
import com.example.course_project_2023.service.exception.likedislike.ReviewNotYetDislikedException;
import com.example.course_project_2023.service.exception.likedislike.ReviewNotYetLikedException;
import com.example.course_project_2023.service.exception.notFound.ReviewNotFoundException;
import com.example.course_project_2023.service.exception.notFound.TeacherNotFoundException;
import com.example.course_project_2023.service.mappers.ReviewMapper;
import com.example.course_project_2023.service.mappers.ReviewViewMapper;
import com.example.course_project_2023.service.util.LikingUtil;
import com.example.course_project_2023.service.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewViewRepository reviewViewRepository;
    private final ReviewViewMapper reviewViewMapper;
    private final UserUtil userSecurityUtil;
    private final LikingUtil likingUtil;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final ReviewMapper reviewMapper = ReviewMapper.INSTANCE;

    public Page<ReviewDto> findAllByTeachersId(Long teacherId, int pageNumber, int pageSize) {
        Long userId = userSecurityUtil.getUserIdFromContext();
        return reviewViewRepository.findReviewsByTeacherId(teacherId, PageRequest.of(pageNumber, pageSize))
                .map((ent) -> reviewViewMapper.reviewDtoFromReviewView(ent, userId));
    }


    public void dislikeReview(Long reviewId) {
        likingUtil.dislike(userSecurityUtil.getUserIdFromContext(), reviewId);
    }

    public void likeReview(Long reviewId) {
        likingUtil.like(userSecurityUtil.getUserIdFromContext(), reviewId);
    }

    public DislikeOnReviewResponseDto dislikeReviewV2(Long reviewId) {
        Long userIdFromContext = userSecurityUtil.getUserIdFromContext();
        if (likingUtil.isDisliked(userIdFromContext, reviewId))
            throw new ReviewAlreadyDislikedException(reviewId, userIdFromContext);
        if (likingUtil.isLiked(userIdFromContext, reviewId))
            throw new ReviewAlreadyLikedException(reviewId, userIdFromContext);
        likingUtil.dislikeV2(userIdFromContext, reviewId);
        Long numberOfDislikes = reviewViewRepository.getDislikeCountByReviewId(reviewId);
        return new DislikeOnReviewResponseDto(numberOfDislikes, true);
    }

    public DislikeOnReviewResponseDto unDislikeReviewV2(Long reviewId) {
        Long userIdFromContext = userSecurityUtil.getUserIdFromContext();
        if (!likingUtil.isDisliked(userIdFromContext, reviewId))
            throw new ReviewNotYetDislikedException(reviewId, userIdFromContext);
        likingUtil.unDislikeV2(userIdFromContext, reviewId);
        Long numberOfDislikes = reviewViewRepository.getDislikeCountByReviewId(reviewId);
        return new DislikeOnReviewResponseDto(numberOfDislikes, false);
    }

    public LikeOnReviewResponseDto likeReviewV2(Long reviewId) {
        Long userIdFromContext = userSecurityUtil.getUserIdFromContext();
        if (likingUtil.isLiked(userIdFromContext, reviewId))
            throw new ReviewAlreadyLikedException(reviewId, userIdFromContext);
        if (likingUtil.isDisliked(userIdFromContext, reviewId))
            throw new ReviewAlreadyDislikedException(reviewId, userIdFromContext);
        likingUtil.likeV2(userIdFromContext, reviewId);
        Long numberOfLikes = reviewViewRepository.getLikeCountByReviewId(reviewId);
        return new LikeOnReviewResponseDto(numberOfLikes, true);
    }

    public LikeOnReviewResponseDto unLikeReviewV2(Long reviewId) {
        Long userIdFromContext = userSecurityUtil.getUserIdFromContext();
        if (!likingUtil.isLiked(userIdFromContext, reviewId))
            throw new ReviewNotYetLikedException(reviewId, userIdFromContext);
        likingUtil.unlikeV2(userIdFromContext, reviewId);
        Long numberOfLikes = reviewViewRepository.getLikeCountByReviewId(reviewId);
        return new LikeOnReviewResponseDto(numberOfLikes, false);
    }

    public ShortReviewDto getUserReviewByTeacherId(Long teacherId) {
        Long userId = userSecurityUtil.getUserIdFromContext();
        return reviewMapper.getShortReviewDtoFromReview(reviewRepository.findByTeacherIdAndUserId(teacherId, userId).orElseThrow(
                () -> new ReviewNotFoundException(String.format("review from user %s on teacher %s now found", userId, teacherId))
        ));
    }

    public URI createUserReviewByTeacherId(Long teacherId, ShortReviewDto reviewDto) {
        Long userId = userSecurityUtil.getUserIdFromContext();
        Optional<Review> reviewFromDb = reviewRepository.findByTeacherIdAndUserId(teacherId, userId);
        Review review;
        if (reviewFromDb.isPresent()) {
            review = reviewFromDb.get();
            review.setScore(reviewDto.score());
            review.setReviewText(reviewDto.reviewText());
            review.setSubjectName(reviewDto.subjectName());
        } else {
            review = new Review();
            review.setUser(userRepository.findById(userId).get());
            review.setTeacher(teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new TeacherNotFoundException(String.format("Teacher with id %s not found", teacherId)))
            );
            review.setReviewText(reviewDto.reviewText());
            review.setSubjectName(reviewDto.subjectName());
            review.setScore(reviewDto.score());
        }
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/knowyourteacher-api/v1/reviews/")
                .path(reviewRepository.save(review).getId().toString())
                .build().toUri();
    }

    @Transactional
    public void deleteUserReviewByTeacherId(Long teacherId) {
        reviewRepository.deleteByTeacherIdAndUserId(teacherId, userSecurityUtil.getUserIdFromContext());
    }

    public Page<SearchedReviewDto> findBySearchParams(int pageNumber, int pageSize, Map<String, String> searchParams) {
        Long userId = userSecurityUtil.getUserIdFromContext();
        return reviewViewRepository.findByParams(searchParams, PageRequest.of(pageNumber, pageSize))
                .map((ent) -> reviewViewMapper.searchedReviewDtoFromReviewView(ent, userId));
    }
}
