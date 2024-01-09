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
}
