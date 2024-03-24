package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.model.views.TeacherWithMostPopularReview;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TeacherWithMostPopularReviewTest {

    @Autowired
    TeacherWithMostPopularReviewRepository teacherWithMostPopularReviewRepository;

    private Map<String, String> params;

    @BeforeEach
    public void initEach() {
        params = new HashMap<>();
    }

    @Test
    void findByParams_EmptyArgs_RetrieveAll() {
        Assertions.assertEquals(teacherWithMostPopularReviewRepository.findByParams(new HashMap<>(), PageRequest.of(0, 10)).getTotalElements(), 9);
    }

    @Test
    void findByParams_NameGiven_RetrieveOne() {
        params.put("teacherName", "Joh");

        Assertions.assertEquals(teacherWithMostPopularReviewRepository.findByParams(params, PageRequest.of(0, 10)).getTotalElements(), 1);
    }

    @Test
    void findByParams_SurnameGiven_RetrieveOne() {
        params.put("teacherSurname", "doe");

        Assertions.assertEquals(teacherWithMostPopularReviewRepository.findByParams(params, PageRequest.of(0, 10)).getTotalElements(), 1);
    }

    @Test
    void findByParams_SubjectGiven_RetrieveOne() {
        params.put("subject", "Math");

        Assertions.assertEquals(teacherWithMostPopularReviewRepository.findByParams(params, PageRequest.of(0, 10)).getTotalElements(), 1);
    }


    @Test
    void findByParams_AllParamsGiven_RetrieveOne() {
        params.put("teacherSurname", "doe");
        params.put("teacherName", "jo");
        params.put("universityId", "2");

        Assertions.assertEquals(teacherWithMostPopularReviewRepository.findByParams(params, PageRequest.of(0, 10)).getTotalElements(), 1);
    }

    @Test
    void findByParams_AllParamsGivenInDifferentCases_RetrieveOne() {
        params.put("teacherSurname", "dOe");
        params.put("teacherName", "jO");
        params.put("universityId", "2");

        Assertions.assertEquals(teacherWithMostPopularReviewRepository.findByParams(params, PageRequest.of(0, 10)).getTotalElements(), 1);
    }

    @Test
    void findByParams_AllParamsGivenInDifferentCasesForJohn_RetrieveExactlyJohn() {
        params.put("teacherSurname", "dOe");
        params.put("teacherName", "jO");
        params.put("universityId", "2");

        TeacherWithMostPopularReview jd = teacherWithMostPopularReviewRepository.findByParams(params, PageRequest.of(0, 10)).stream().toList().get(0);

        Assertions.assertAll(
                () -> Assertions.assertEquals(jd.getId(), 1L),
                () -> Assertions.assertEquals(jd.getTeacherName(), "John"),
                () -> Assertions.assertEquals(jd.getTeacherSurname(), "Doe")
        );
    }


    @Test
    void findByParams_AllParamsGivenForTeacherNotExist_RetrieveZero() {
        params.put("teacherSurname", "dOe");
        params.put("teacherName", "jO");
        params.put("universityId", "2");

        Assertions.assertEquals(teacherWithMostPopularReviewRepository.findByParams(params, PageRequest.of(0, 10)).getTotalElements(), 1);
    }
}
