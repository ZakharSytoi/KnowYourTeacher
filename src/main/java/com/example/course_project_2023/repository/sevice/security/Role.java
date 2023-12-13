package com.example.course_project_2023.repository.sevice.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.course_project_2023.repository.sevice.security.Permission.*;

public enum Role {

    ADMIN(Set.of(
            ADMIN_PERMISSION
    )),
    USER(Set.of(
            USER_PERMISSION
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
