package com.example.course_project_2023.repository.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ReviewViewRepositoryTest {
    @Autowired
    ReviewViewRepository reviewViewRepository;

    @Test
    void retrieveBySubjectTest() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("subject", "Math");
        reviewViewRepository.findByParams(paramMap, null).forEach(System.out::println);
    }

    @Test
    void retrieveByTeacherNameTest() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("teacherName", "Joh");
        reviewViewRepository.findByParams(paramMap, null).forEach(System.out::println);
    }

    @Test
    void retrieveByTeacherSurnameTest() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("teacherSurname", "doe");
        reviewViewRepository.findByParams(paramMap, null).forEach(System.out::println);
    }

    @Test
    void retrieveByTeacherNameAndSurnameTest() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("teacherSurname", "doe");
        paramMap.put("teacherName", "jo");
        reviewViewRepository.findByParams(paramMap, null).forEach(System.out::println);
    }

    @Test
    void retrieveByUniversityIdTest() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("universityId", "10");
        reviewViewRepository.findByParams(paramMap, null).forEach(System.out::println);
    }

    @Test
    void retrieveAllParamsTest() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("teacherSurname", "doe");
        paramMap.put("teacherName", "jo");
        paramMap.put("universityId", "2");
        reviewViewRepository.findByParams(paramMap, null).forEach(System.out::println);
    }
}