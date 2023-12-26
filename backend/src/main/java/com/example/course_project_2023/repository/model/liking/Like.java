package com.example.course_project_2023.repository.model.liking;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "likes")
public class Like {
    @EmbeddedId
    private ComposeId composeId;
}
