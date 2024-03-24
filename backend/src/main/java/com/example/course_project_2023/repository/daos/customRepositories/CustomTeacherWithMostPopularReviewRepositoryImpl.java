package com.example.course_project_2023.repository.daos.customRepositories;

import com.example.course_project_2023.repository.daos.TeacherRepository;
import com.example.course_project_2023.repository.daos.TeacherWithMostPopularReviewRepository;
import com.example.course_project_2023.repository.model.views.TeacherWithMostPopularReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CustomTeacherWithMostPopularReviewRepositoryImpl implements CustomTeacherWithMostPopularReviewRepository {

    @Autowired
    TeacherRepository teacherRepository;

    private TeacherWithMostPopularReviewRepository teacherWithMostPopularReviewRepository;

    /*
    @Autowired
    public CustomTeacherWithMostPopularReviewRepositoryImpl(TeacherWithMostPopularReviewRepository teacherWithMostPopularReviewRepository) {
        this.teacherWithMostPopularReviewRepository = teacherWithMostPopularReviewRepository;
    }*/

    @Autowired
    void setTeacherWithMostPopularReviewRepository(@Lazy TeacherWithMostPopularReviewRepository teacherWithMostPopularReviewRepository) {
        this.teacherWithMostPopularReviewRepository = teacherWithMostPopularReviewRepository;
    }

    @Override
    public Page<TeacherWithMostPopularReview> findByParams(Map<String, String> params, Pageable pageRequest) {
        List<Long> ids = teacherRepository.findByParamsReturnIds(params);

        List<TeacherWithMostPopularReview> teacherWithMostPopularReviewsList = teacherWithMostPopularReviewRepository.findAllById(ids);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), teacherWithMostPopularReviewsList.size());
        return new PageImpl<>(teacherWithMostPopularReviewsList.subList(start, end), pageRequest, teacherWithMostPopularReviewsList.size());
    }
}
