package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.UniversityRepository;
import com.example.course_project_2023.repository.daos.UserRepository;
import com.example.course_project_2023.repository.model.SecurityUser;
import com.example.course_project_2023.repository.model.User;
import com.example.course_project_2023.service.dto.UserLoginDtoRequest;
import com.example.course_project_2023.service.dto.UserRegistrationDtoRequest;
import com.example.course_project_2023.service.exception.UserWithEmailAlreadyExistsException;
import com.example.course_project_2023.service.exception.UserWithNicknameAlreadyExistsException;
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
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailServiceImpl userDetailService;
    private final SecurityUserService securityUserService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;
    private final UserService userService;
    public String authenticateUser(UserLoginDtoRequest userLoginDtoRequest){
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
    public void registerUser(UserRegistrationDtoRequest request) throws UserWithEmailAlreadyExistsException, UserWithNicknameAlreadyExistsException {
        if(securityUserService.findByEmail(request.email()).isPresent()){
            throw new UserWithEmailAlreadyExistsException(String.format("User with email %s already exists.", request.email()));
        }
        if(userService.existsWithNickname(request.nickname())){
            throw new UserWithNicknameAlreadyExistsException(String.format("User with nickname %s already exists.", request.nickname()));
        }
        User user = new User();
        user.setNickname(request.nickname());
        user.setUniversity(universityRepository.findById(request.universityId()).get());
        user.setFieldOfStudies(request.fieldOfStudies());

        SecurityUser securityUser = new SecurityUser();
        securityUser.setEmail(request.email());
        securityUser.setPassword(passwordEncoder.encode(request.password()));
        securityUser.setRoles(Set.of(roleService.findRoleByName("USER")));

        user.setSecurityUser(securityUser);
        securityUser.setUser(user);

        userRepository.save(user);

    }
}
