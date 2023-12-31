package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
