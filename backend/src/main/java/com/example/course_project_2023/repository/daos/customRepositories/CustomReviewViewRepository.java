package com.example.course_project_2023.repository.daos.customRepositories;

import com.example.course_project_2023.repository.model.views.ReviewView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CustomReviewViewRepository{
    Page<ReviewView> findByParams(Map<String, String> params, Pageable pageRequest);
}
