package com.example.course_project_2023.service.exception.likedislike;

public class ReviewNotYetLikedException extends LikeDislikeException{
    public ReviewNotYetLikedException(Long reviewId, Long userId) {
        super(String.format("Review with id %d is not yet liked by user %d", reviewId, userId));
    }
}
