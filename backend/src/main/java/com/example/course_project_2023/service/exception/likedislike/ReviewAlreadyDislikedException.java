package com.example.course_project_2023.service.exception.likedislike;

public class ReviewAlreadyDislikedException extends LikeDislikeException{
    public ReviewAlreadyDislikedException(Long reviewId, Long userId) {
        super(String.format("Review with id %d is already disliked by user %d", reviewId, userId));
    }
}
