package com.example.course_project_2023.service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ShortReviewDto(
        @NotNull(message = "score can not be Null")
        @Min(value = 1, message = "score value must be greater then or equal 1")
        @Max(value = 5, message = "score value must not exceed 5")
        short score,
        @NotNull(message = "subjectName can not be Null")
        @Length(min = 3, max = 50, message = "subjectName length must be between 3 and 50 characters")
        String subjectName,
        @NotNull(message = "reviewText can not be Null")
        @Length(min = 3, max = 1000, message = "reviewText length must be between 3 and 1000 characters")
        String reviewText
) {
}
