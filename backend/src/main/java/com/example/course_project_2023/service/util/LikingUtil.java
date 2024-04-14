package com.example.course_project_2023.service.util;

import com.example.course_project_2023.repository.daos.DislikeRepository;
import com.example.course_project_2023.repository.daos.LikeRepository;
import com.example.course_project_2023.repository.model.liking.ComposeId;
import com.example.course_project_2023.repository.model.liking.Dislike;
import com.example.course_project_2023.repository.model.liking.Like;
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
        if (userId == -1) return null;
        return dislikeRepository.existsById(new ComposeId(userId, reviewId));
    }

    public void like(Long userId, Long reviewId) {
        if(unlike(userId, reviewId)) return;
        undislike(userId, reviewId);
        Like like = new Like();
        like.setComposeId(new ComposeId(userId, reviewId));
        likeRepository.save(like);
    }

    public void likeV2(Long userId, Long reviewId) {
        Like like = new Like();
        like.setComposeId(new ComposeId(userId, reviewId));
        likeRepository.save(like);
    }

    public void unlikeV2(Long userId, Long reviewId) {
        likeRepository.deleteById(new ComposeId(userId, reviewId));
    }
    public void dislikeV2(Long userId, Long reviewId) {
        Dislike dislike = new Dislike();
        dislike.setComposeId(new ComposeId(userId, reviewId));
        dislikeRepository.save(dislike);
    }
    public void unDislikeV2(Long userId, Long reviewId) {
        dislikeRepository.deleteById(new ComposeId(userId, reviewId));
    }




    public void dislike(Long userId, Long reviewId) {
        if(undislike(userId, reviewId)) return;
        unlike(userId, reviewId);
        Dislike dis = new Dislike();
        dis.setComposeId(new ComposeId(userId, reviewId));
        dislikeRepository.save(dis);
    }

    public boolean unlike(Long userId, Long reviewId) {
        if (isLiked(userId, reviewId)){
            likeRepository.deleteById(new ComposeId(userId, reviewId));
            return true;
        }
        return false;
    }

    public boolean undislike(Long userId, Long reviewId) {
        if (isDisliked(userId, reviewId)){
            dislikeRepository.deleteById(new ComposeId(userId, reviewId));
            return true;
        }
        return false;
    }
}
