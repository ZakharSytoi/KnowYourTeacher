package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.ReviewViewRepository;
import com.example.course_project_2023.repository.daos.SecurityUserRepository;
import com.example.course_project_2023.service.dto.ReviewDto;
import com.example.course_project_2023.service.mappers.ReviewViewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewViewRepository reviewViewRepository;
    private final ReviewViewMapper reviewViewMapper;
    private final SecurityUserRepository userRepository;

    public Page<ReviewDto> findAllByTeachersId(Long id, int pageNumber, int pageSize) {
        Long userId;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!userName.equals("anonymousUser")) {
            userId = userRepository.findByEmail(userName).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            ).getId();
        } else userId = -1L;
        return reviewViewRepository.findReviewsByTeacherId(id, PageRequest.of(pageNumber, pageSize))
                .map((ent) -> reviewViewMapper.reviewDtoFromReviewView(ent, userId));
    }

}
