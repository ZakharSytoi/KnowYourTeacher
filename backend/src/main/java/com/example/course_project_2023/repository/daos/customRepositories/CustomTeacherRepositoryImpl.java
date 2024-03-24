package com.example.course_project_2023.repository.daos.customRepositories;

import com.example.course_project_2023.repository.model.Review;
import com.example.course_project_2023.repository.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CustomTeacherRepositoryImpl implements CustomTeacherRepository{
    @PersistenceContext
    EntityManager entityManager;



    @Override
    public List<Teacher> findByParams(Map<String, String> params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = criteriaBuilder.createQuery(Teacher.class);
        Root<Review> root = query.from(Review.class);
        Join<Review, Teacher> teacherJoin = root.join("teacher");

        LinkedList<Predicate> predicates = buildPredicates(criteriaBuilder, root, teacherJoin, params);

        query.select(teacherJoin).distinct(true).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Long> findByParamsReturnIds(Map<String, String> params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Review> root = query.from(Review.class);
        Join<Review, Teacher> teacherJoin = root.join("teacher");

        LinkedList<Predicate> predicates = buildPredicates(criteriaBuilder, root, teacherJoin, params);

        query.select(teacherJoin.get("id")).distinct(true).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    private LinkedList<Predicate> buildPredicates(CriteriaBuilder criteriaBuilder, Root<Review> root, Join<Review, Teacher> teacherJoin, Map<String, String> params) {
        LinkedList<Predicate> predicates = new LinkedList<>();

        if (params.containsKey("subject") && !params.get("subject").equals("")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("subjectName")), "%" + params.get("subject").toLowerCase() + "%"));
        }
        if(params.containsKey("teacherName")&& !params.get("teacherName").equals("")){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(teacherJoin.get("name")), "%" + params.get("teacherName").toLowerCase() + "%"));
        }
        if(params.containsKey("teacherSurname")&& !params.get("teacherSurname").equals("")){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(teacherJoin.get("surname")), "%" + params.get("teacherSurname").toLowerCase() + "%"));
        }
        if(params.containsKey("universityId")&& !params.get("universityId").equals("")){
            predicates.add(criteriaBuilder.equal(teacherJoin.get("university").get("id"), Long.parseLong(params.get("universityId"))));
        }

        return predicates;
    }
}
