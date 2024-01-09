package com.example.course_project_2023.repository.daos.customRepositories;

import com.example.course_project_2023.repository.model.Teacher;
import com.example.course_project_2023.repository.model.University;
import com.example.course_project_2023.repository.model.views.ReviewView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CustomReviewViewRepositoryImpl implements CustomReviewViewRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<ReviewView> findByParams(Map<String, String> params, Pageable pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReviewView> query = criteriaBuilder.createQuery(ReviewView.class);
        Root<ReviewView> root = query.from(ReviewView.class);

        LinkedList<Predicate> predicates = new LinkedList<>();
        if (params.containsKey("subject") && !params.get("subject").equals("")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("subjectName")), "%" + params.get("subject").toLowerCase() + "%"));
        }
        if(params.containsKey("teacherName")&& !params.get("teacherName").equals("")){
            Join<ReviewView, Teacher> teacherJoin = root.join("teacher");
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(teacherJoin.get("name")), "%" + params.get("teacherName").toLowerCase() + "%"));
        }
        if(params.containsKey("teacherSurname")&& !params.get("teacherSurname").equals("")){
            Join<ReviewView, Teacher> teacherJoin = root.join("teacher");
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(teacherJoin.get("surname")), "%" + params.get("teacherSurname").toLowerCase() + "%"));
        }
        if(params.containsKey("universityId")&& !params.get("universityId").equals("")){
            Join<ReviewView, Teacher> teacherJoin = root.join("teacher");
            Join<Teacher, University> universityJoin = teacherJoin.join("university");
            predicates.add(criteriaBuilder.equal(universityJoin.get("id"), Long.parseLong(params.get("universityId"))));
        }

        query.select(root).where(predicates.toArray(new Predicate[0]));
        List<ReviewView> resultList = entityManager.createQuery(query).getResultList();
        return new PageImpl<>(resultList, pageRequest, resultList.size());
    }
}
