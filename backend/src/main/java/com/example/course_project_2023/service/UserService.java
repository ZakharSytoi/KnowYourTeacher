package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.ReviewViewRepository;
import com.example.course_project_2023.repository.daos.SecurityUserRepository;
import com.example.course_project_2023.repository.daos.UniversityRepository;
import com.example.course_project_2023.repository.daos.UserRepository;
import com.example.course_project_2023.repository.model.SecurityUser;
import com.example.course_project_2023.repository.model.User;
import com.example.course_project_2023.service.dto.PasswordUpdateDtoRequest;
import com.example.course_project_2023.service.dto.SearchedReviewDto;
import com.example.course_project_2023.service.dto.UserInfoUpdateDtoRequest;
import com.example.course_project_2023.service.dto.UserResponseDto;
import com.example.course_project_2023.service.exception.UserWithNicknameAlreadyExistsException;
import com.example.course_project_2023.service.exception.notFound.UniversityNotFoundException;
import com.example.course_project_2023.service.mappers.ReviewViewMapper;
import com.example.course_project_2023.service.mappers.UserMapper;
import com.example.course_project_2023.service.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUserRepository securityUserRepository;
    private final ReviewViewRepository reviewViewRepository;
    private final UniversityRepository universityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userSecurityUtil;
    private final ReviewViewMapper reviewViewMapper;

    public UserResponseDto getUserInfo() {
        Long userId = userSecurityUtil.getUserIdFromContext();
        return UserMapper.INSTANCE.userToUserResponseDto(userRepository.findWithUniversityById(userId).get());
    }

    boolean existsWithNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public void updateUserPassword(PasswordUpdateDtoRequest passwordUpdateDtoRequest) {
        Long userId = userSecurityUtil.getUserIdFromContext();
        SecurityUser securityUser = securityUserRepository.findById(userId).get();
        String fromDb = securityUser.getPassword();

        if(!passwordEncoder.matches(passwordUpdateDtoRequest.currentPassword(), fromDb))
            throw new BadCredentialsException("Passwords mismatch");

        securityUser.setPassword(passwordEncoder.encode(passwordUpdateDtoRequest.newPassword()));
        securityUserRepository.save(securityUser);
    }

    public Page<SearchedReviewDto> findAllUserReviews(int pageNumber, int pageSize) {
        Long userId = userSecurityUtil.getUserIdFromContext();
        return reviewViewRepository.findAllByUserId(userId, PageRequest.of(pageNumber, pageSize))
                .map((ent) -> reviewViewMapper.searchedReviewDtoFromReviewView(ent, userId));
    }

    public void updateUserInfo(UserInfoUpdateDtoRequest userInfoUpdateDtoRequest) {
        Long userId = userSecurityUtil.getUserIdFromContext();

        User user = userRepository.findById(userId).get();

        if (!user.getNickname().equals(userInfoUpdateDtoRequest.nickname())
                && existsWithNickname(userInfoUpdateDtoRequest.nickname())) {
            throw new UserWithNicknameAlreadyExistsException(
                    String.format("User with nickname %s already exists.", userInfoUpdateDtoRequest.nickname())
            );
        }

        user.setNickname(userInfoUpdateDtoRequest.nickname());
        user.setUniversity(universityRepository.findById(userInfoUpdateDtoRequest.universityId()).orElseThrow(
                () -> new UniversityNotFoundException(String.format("University with id = %d not found", userInfoUpdateDtoRequest.universityId())))
        );
        user.setFieldOfStudies(userInfoUpdateDtoRequest.fieldOfStudies());

        userRepository.save(user);
    }
}
