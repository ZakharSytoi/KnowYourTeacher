package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.UserRepository;
import com.example.course_project_2023.service.dto.UserResponseDto;
import com.example.course_project_2023.service.mappers.UserMapper;
import com.example.course_project_2023.service.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserUtil userSecurityUtil;

    public UserResponseDto getUserInfo() {
        Long userId = userSecurityUtil.getUserIdFromContext();
        return UserMapper.INSTANCE.userToUserResponseDto(userRepository.findById(userId).get());
    }

    boolean existsWithNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
