package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.model.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomTeacherRepositoryTest {

    @Autowired
    TeacherRepository teacherRepository;

    private Map<String, String> params;

    @BeforeEach
    public void initEach() {
        params = new HashMap<>();
    }

    @Test
    void findByParams_EmptyArgs_RetrieveAll() {
        Assertions.assertEquals(teacherRepository.findByParams(new HashMap<>()).size(), 9);
    }

    @Test
    void findByParams_NameGiven_RetrieveOne() {
        params.put("teacherName", "Joh");

        Assertions.assertEquals(teacherRepository.findByParams(params).size(), 1);
    }

    @Test
    void findByParams_SurnameGiven_RetrieveOne() {
        params.put("teacherSurname", "doe");

        Assertions.assertEquals(teacherRepository.findByParams(params).size(), 1);
    }

    @Test
    void findByParams_SubjectGiven_RetrieveOne() {
        params.put("subject", "Math");

        Assertions.assertEquals(teacherRepository.findByParams(params).size(), 1);
    }


    @Test
    void findByParams_AllParamsGiven_RetrieveOne() {
        params.put("teacherSurname", "doe");
        params.put("teacherName", "jo");
        params.put("universityId", "2");

        Assertions.assertEquals(teacherRepository.findByParams(params).size(), 1);
    }

    @Test
    void findByParams_AllParamsGivenInDifferentCases_RetrieveOne() {
        params.put("teacherSurname", "dOe");
        params.put("teacherName", "jO");
        params.put("universityId", "2");

        Assertions.assertEquals(teacherRepository.findByParams(params).size(), 1);
    }

    @Test
    void findByParams_AllParamsGivenInDifferentCasesForJohn_RetrieveExactlyJohn() {
        params.put("teacherSurname", "dOe");
        params.put("teacherName", "jO");
        params.put("universityId", "2");

        Teacher jd = teacherRepository.findByParams(params).get(0);

        Assertions.assertAll(
                () -> Assertions.assertEquals(jd.getId(), 1L),
                () -> Assertions.assertEquals(jd.getName(), "John"),
                () -> Assertions.assertEquals(jd.getSurname(), "Doe")
        );
    }


    @Test
    void findByParams_AllParamsGivenForTeacherNotExist_RetrieveZero() {
        params.put("teacherSurname", "dOe");
        params.put("teacherName", "jO");
        params.put("universityId", "2");

        Assertions.assertEquals(teacherRepository.findByParams(params).size(), 1);
    }

    @Test
    void findByParamsReturnIds_EmptyArgs_RetrieveAll() {
        Assertions.assertEquals(teacherRepository.findByParamsReturnIds(new HashMap<>()).size(), 9);
    }

    @Test
    void findByParamsReturnIds_NameGiven_RetrieveOne() {
        params.put("teacherName", "Joh");

        Assertions.assertEquals(teacherRepository.findByParamsReturnIds(params).size(), 1);
    }

    @Test
    void findByParamsReturnIds_SurnameGiven_RetrieveOne() {
        params.put("teacherSurname", "doe");

        Assertions.assertEquals(teacherRepository.findByParamsReturnIds(params).size(), 1);
    }

    @Test
    void findByParamsReturnIds_SubjectGiven_RetrieveOne() {
        params.put("subject", "Math");

        Assertions.assertEquals(teacherRepository.findByParamsReturnIds(params).size(), 1);
    }


    @Test
    void findByParamsReturnIds_AllParamsGiven_RetrieveOne() {
        params.put("teacherSurname", "doe");
        params.put("teacherName", "jo");
        params.put("universityId", "2");

        Assertions.assertEquals(teacherRepository.findByParamsReturnIds(params).size(), 1);
    }

    @Test
    void findByParamsReturnIds_AllParamsGivenInDifferentCases_RetrieveOne() {
        params.put("teacherSurname", "dOe");
        params.put("teacherName", "jO");
        params.put("universityId", "2");

        Assertions.assertEquals(teacherRepository.findByParamsReturnIds(params).size(), 1);
    }

    @Test
    void findByParamsReturnIds_AllParamsGivenInDifferentCasesForJohn_RetrieveExactlyJohn() {
        params.put("teacherSurname", "dOe");
        params.put("teacherName", "jO");
        params.put("universityId", "2");

        Long retrievedId = teacherRepository.findByParamsReturnIds(params).get(0);

        Assertions.assertEquals(retrievedId, 1L);
    }


    @Test
    void findByParamsReturnIds_AllParamsGivenForTeacherNotExist_RetrieveZero() {
        params.put("teacherSurname", "dOe");
        params.put("teacherName", "jO");
        params.put("universityId", "2");

        Assertions.assertEquals(teacherRepository.findByParamsReturnIds(params).size(), 1);
    }
}
