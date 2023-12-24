package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.RoleRepository;
import com.example.course_project_2023.repository.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role findRoleByName(String name){
        return roleRepository.findByName(name).get();
    }
}
