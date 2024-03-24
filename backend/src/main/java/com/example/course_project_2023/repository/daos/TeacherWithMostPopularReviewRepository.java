package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.daos.customRepositories.CustomTeacherWithMostPopularReviewRepository;
import com.example.course_project_2023.repository.model.views.TeacherWithMostPopularReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherWithMostPopularReviewRepository extends JpaRepository<TeacherWithMostPopularReview, Long>, CustomTeacherWithMostPopularReviewRepository {

}
