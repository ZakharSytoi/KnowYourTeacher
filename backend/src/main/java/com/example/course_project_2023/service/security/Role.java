package com.example.course_project_2023.service.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    ADMIN(Set.of(
            Permission.ADMIN_SPECIFIC_PERMISSION
    )),
    USER(Set.of(
            Permission.USER_SPECIFIC_PERMISSION
    ));


    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    }
    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return this.permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.toString()))
                .collect(Collectors.toSet());
    }
}
