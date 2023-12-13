package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
