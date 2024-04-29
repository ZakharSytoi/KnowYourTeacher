package com.example.course_project_2023.repository.daos;

import com.example.course_project_2023.repository.model.views.ReviewView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @ArgumentsSource(UserIdAndNumberOfUserReviewsArgumentsProvider.class)
    void givenUserId_WhenFindAllByUserId_ThenCorrectNumberOfReviewViewsReturned(Long userId, Long expected) {

        // Given

        // When
        long totalReviews = reviewViewRepository.findAllByUserId(userId, PageRequest.of(0, 10))
                .getTotalElements();

        // Then
        Assertions.assertEquals(expected, totalReviews);
    }

    @ParameterizedTest
    @ArgumentsSource(UserIdUserReviewIdsArgumentsProvider.class)
    void givenUserId_WhenFindAllByUserId_ThenCorrectReviewIdsReturned(Long userId, Long[] expectedReviewIds) {

        // Given

        // When
        Page<ReviewView> reviewViews = reviewViewRepository.findAllByUserId(userId, PageRequest.of(0, 10));
        Long[] actualReviewIds = reviewViews.stream().map(ReviewView::getId).sorted().toArray(Long[]::new);

        // Then
        Assertions.assertArrayEquals(expectedReviewIds, actualReviewIds);
    }

    static class UserIdAndNumberOfUserReviewsArgumentsProvider implements ArgumentsProvider{
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(7L, 3L),
                    Arguments.of(8L, 3L),
                    Arguments.of(10L, 2L)
            );
        }
    }

    static class UserIdUserReviewIdsArgumentsProvider implements ArgumentsProvider{
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(7L, new Long[]{6L, 11L, 16L}),
                    Arguments.of(8L, new Long[]{7L, 12L, 17L}),
                    Arguments.of(10L, new Long[]{9L, 14L})
            );
        }
    }
}