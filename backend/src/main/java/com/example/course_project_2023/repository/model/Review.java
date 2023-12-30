package com.example.course_project_2023.repository.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "review")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "score")
    private Short score;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @Column(name = "subject_name")
    private String subjectName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "review_text")
    private String reviewText;
    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;
}
