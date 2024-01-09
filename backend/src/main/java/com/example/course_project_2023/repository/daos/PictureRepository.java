package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, UUID> {
    @Query("SELECT picture FROM Picture picture WHERE CAST(picture.id AS string)= ?1")
    Optional<Picture> findPictureById(String id);
}
