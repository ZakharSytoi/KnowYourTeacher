package com.example.course_project_2023.service.exception.likedislike;

public class ReviewNotYetDislikedException extends LikeDislikeException{
    public ReviewNotYetDislikedException(Long reviewId, Long userId) {
        super(String.format("Review with id %d is not yet disliked by user %d", reviewId, userId));
    }
}
