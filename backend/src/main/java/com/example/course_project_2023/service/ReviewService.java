package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.ReviewViewRepository;
import com.example.course_project_2023.service.dto.ReviewDto;
import com.example.course_project_2023.service.mappers.ReviewViewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewViewRepository reviewViewRepository;
    private final ReviewViewMapper reviewViewMapper = ReviewViewMapper.INSTANCE;

    public List<ReviewDto> findAllByTeachersId(Long id) {
        return reviewViewMapper.reviewDtoListFromReviewViewList(reviewViewRepository.findReviewsByTeacherId(id));
    }

}
