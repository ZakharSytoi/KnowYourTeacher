package com.example.course_project_2023.repository.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Immutable;

@Entity
@Table(name = "top_teachers_with_most_popular_review_text")
@Data
@Immutable
public class TeacherWithMostPopularReview {
    @Id
    @Column(name = "teacher_id")
    private Long id;
    @Column(name = "teacher_name")
    private String teacherName;
    @Column(name = "teacher_surname")
    private String teacherSurname;
    @Column(name = "university_name")
    private String universityName;
    @Column(name = "avg_score")
    private Double avgScore;
    @Column(name = "most_popular_review_text")
    private String mostPopularReviewText;
}
