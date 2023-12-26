package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.model.views.ReviewView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewViewRepository extends JpaRepository<ReviewView, Long> {
    @Query("SELECT r FROM ReviewView r WHERE r.teacher.id = ?1")
    List<ReviewView> findReviewsByTeacherId(Long id);
}
