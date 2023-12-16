package com.example.course_project_2023.sevice;

import com.example.course_project_2023.repository.daos.SecurityUserRepository;
import com.example.course_project_2023.repository.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityUserService {
    private final RoleService roleservice;
    private final SecurityUserRepository securityUserRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<SecurityUser> findByEmail(String email) {
        return securityUserRepository.findByEmail(email);
    }


}
