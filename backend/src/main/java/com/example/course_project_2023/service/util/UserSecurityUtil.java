package com.example.course_project_2023.service.util;

import com.example.course_project_2023.repository.daos.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSecurityUtil {
    private final SecurityUserRepository userRepository;
    public Long getUserIdFromContext(){
        Long userId;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!userName.equals("anonymousUser")) {
            userId = userRepository.findByEmail(userName).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            ).getId();
        } else userId = -1L;
        return userId;
    }
}
