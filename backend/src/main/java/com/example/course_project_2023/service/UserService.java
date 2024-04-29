package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.SecurityUserRepository;
import com.example.course_project_2023.repository.daos.UserRepository;
import com.example.course_project_2023.repository.model.SecurityUser;
import com.example.course_project_2023.service.dto.PasswordUpdateDtoRequest;
import com.example.course_project_2023.service.dto.UserResponseDto;
import com.example.course_project_2023.service.mappers.UserMapper;
import com.example.course_project_2023.service.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUserRepository securityUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userSecurityUtil;

    public UserResponseDto getUserInfo() {
        Long userId = userSecurityUtil.getUserIdFromContext();
        return UserMapper.INSTANCE.userToUserResponseDto(userRepository.findWithUniversityById(userId).get());
    }

    boolean existsWithNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public void updateUserPassword(PasswordUpdateDtoRequest passwordUpdateDtoRequest) {
        Long userId = userSecurityUtil.getUserIdFromContext();
        SecurityUser securityUser = securityUserRepository.findById(userId).get();
        securityUser.setPassword(passwordEncoder.encode(passwordUpdateDtoRequest.password()));
        securityUserRepository.save(securityUser);
    }
}
