package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.DislikeRepository;
import com.example.course_project_2023.repository.daos.LikeRepository;
import com.example.course_project_2023.repository.model.liking.ComposeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikingUtil {
    private final LikeRepository likeRepository;
    private final DislikeRepository dislikeRepository;


    public Boolean isLiked(Long userId, Long reviewId) {
        if (userId == -1) return null;
        return likeRepository.existsById(new ComposeId(userId, reviewId));
    }

    public Boolean isDisliked(Long userId, Long reviewId) {
        if(userId == -1) return null;
        return dislikeRepository.existsById(new ComposeId(userId, reviewId));
    }
}
