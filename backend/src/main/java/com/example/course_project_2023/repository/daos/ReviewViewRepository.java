package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.daos.customRepositories.CustomReviewViewRepository;
import com.example.course_project_2023.repository.model.views.ReviewView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewViewRepository extends JpaRepository<ReviewView, Long>, CustomReviewViewRepository {
    @Query("SELECT r FROM ReviewView r WHERE r.teacher.id = ?1")
    Page<ReviewView> findReviewsByTeacherId(Long id, Pageable pageable);

    @Query("SELECT rv FROM ReviewView rv JOIN Review r ON rv.id = r.id WHERE r.user.id = ?1")
    Page<ReviewView> findAllByUserId(Long id, Pageable pageable);

    @Query("SELECT r.likeCount FROM ReviewView r WHERE r.id = ?1")
    Long getLikeCountByReviewId(Long reviewId);

    @Query("SELECT r.dislikeCount FROM ReviewView r WHERE r.id = ?1")
    Long getDislikeCountByReviewId(Long reviewId);
}
