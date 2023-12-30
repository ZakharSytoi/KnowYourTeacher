package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByTeacherIdAndUserId(Long teacherId, Long userId);
    void deleteByTeacherIdAndUserId(Long teacherId, Long userId);

}
