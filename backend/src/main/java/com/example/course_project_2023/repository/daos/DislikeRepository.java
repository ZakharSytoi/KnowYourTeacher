package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.model.liking.ComposeId;
import com.example.course_project_2023.repository.model.liking.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DislikeRepository extends JpaRepository<Dislike, ComposeId> {
}
