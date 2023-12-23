package com.example.course_project_2023.repository.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@Table(name = "teacher")
@EntityListeners(AuditingEntityListener.class)
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "avg_score")
    private Double avgScore;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;
    @ManyToOne()
    @JoinColumn(name = "university_id")
    private University university;
    @ManyToOne()
    @JoinColumn(name = "created_by")
    private User user;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date modifiedDate;
}
