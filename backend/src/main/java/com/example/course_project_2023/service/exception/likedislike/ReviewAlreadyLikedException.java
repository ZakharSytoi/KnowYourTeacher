package com.example.course_project_2023.service.exception.likedislike;

public class ReviewAlreadyLikedException extends LikeDislikeException{
    public ReviewAlreadyLikedException(Long reviewId, Long userId) {
        super(String.format("Review with id %d is already liked by user %d", reviewId, userId));
    }
}
