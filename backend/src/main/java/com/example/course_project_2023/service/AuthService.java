package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.UniversityRepository;
import com.example.course_project_2023.repository.daos.UserRepository;
import com.example.course_project_2023.repository.model.SecurityUser;
import com.example.course_project_2023.repository.model.User;
import com.example.course_project_2023.repository.redis.redismodels.UserRegistration;
import com.example.course_project_2023.repository.redis.redisrepos.UserRegistrationRepository;
import com.example.course_project_2023.service.dto.UserLoginDtoRequest;
import com.example.course_project_2023.service.dto.UserRegisterDtoRequest;
import com.example.course_project_2023.service.dto.kafka.RegistrationAttempt;
import com.example.course_project_2023.service.exception.UserWithEmailAlreadyExistsException;
import com.example.course_project_2023.service.exception.UserWithNicknameAlreadyExistsException;
import com.example.course_project_2023.service.exception.notFound.ProfileNotFoundException;
import com.example.course_project_2023.service.kafka.KafkaService;
import com.example.course_project_2023.service.security.UserDetailServiceImpl;
import com.example.course_project_2023.service.security.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final UserRegistrationRepository userRegistrationRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailServiceImpl userDetailService;
    private final SecurityUserService securityUserService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;
    private final UserService userService;
    private final KafkaService kafkaService;

    public String authenticateUser(UserLoginDtoRequest userLoginDtoRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDtoRequest.getUsername(),
                        userLoginDtoRequest.getPassword()
                )
        );
        UserDetails userDetails = userDetailService.loadUserByUsername(
                userLoginDtoRequest.getUsername());
        return jwtUtil.generateToken(userDetails);
    }

    @Transactional
    public void registerUser(UserRegisterDtoRequest request) throws UserWithEmailAlreadyExistsException, UserWithNicknameAlreadyExistsException {
        if (securityUserService.findByEmail(request.email()).isPresent() || userRegistrationRepository.existsByEmail(request.email())) {
            throw new UserWithEmailAlreadyExistsException(String.format("User with email %s already exists.", request.email()));
        }
        if (userService.existsWithNickname(request.nickname()) || userRegistrationRepository.existsByNickname(request.nickname())) {
            throw new UserWithNicknameAlreadyExistsException(String.format("User with nickname %s already exists.", request.nickname()));
        }

        UserRegistration userRegistration = userRegistrationRepository.save(new UserRegistration(request));
        kafkaService.sendRegistrationAttemptMessage(new RegistrationAttempt(userRegistration.getEmail(), userRegistration.getId()));
    }

    public void activateProfile(String id) {
        UserRegistration registration = userRegistrationRepository.findById(id)
                .orElseThrow(
                        () -> new ProfileNotFoundException("There is no such profile to be activated")
                );
        UserRegisterDtoRequest registrationRequest = new UserRegisterDtoRequest(
                registration.getNickname(),
                registration.getUniversityId(),
                registration.getFieldOfStudies(),
                registration.getEmail(),
                registration.getPassword()
        );
        User user = new User();
        user.setNickname(registrationRequest.nickname());
        user.setUniversity(universityRepository.findById(registrationRequest.universityId()).get());
        user.setFieldOfStudies(registrationRequest.fieldOfStudies());

        SecurityUser securityUser = new SecurityUser();
        securityUser.setEmail(registrationRequest.email());
        securityUser.setPassword(passwordEncoder.encode(registrationRequest.password()));
        securityUser.setRoles(Set.of(roleService.findRoleByName("USER")));

        user.setSecurityUser(securityUser);
        securityUser.setUser(user);

        userRepository.save(user);
        userRegistrationRepository.deleteById(id);

    }
}
