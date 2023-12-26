package com.example.course_project_2023.repository.model.liking;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;

@Embeddable
@Getter
public class ComposeId implements Serializable {
    private Long userId;
    private Long reviewId;

    public ComposeId(Long userId, Long reviewId) {
        this.userId = userId;
        this.reviewId = reviewId;
    }

    public ComposeId() {

    }
}
