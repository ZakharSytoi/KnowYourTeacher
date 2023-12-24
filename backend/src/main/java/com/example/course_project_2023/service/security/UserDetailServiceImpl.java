package com.example.course_project_2023.service.security;

import com.example.course_project_2023.repository.model.SecurityUser;
import com.example.course_project_2023.service.SecurityUserService;;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final SecurityUserService securityUserService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SecurityUser user = securityUserService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("" +
                String.format("User with email '%s' is not found", email)));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getUserAuthorities(user)
        );
    }

    private Set<SimpleGrantedAuthority> getUserAuthorities(SecurityUser user){
        return user.getRoles()
                .stream()
                .map(role -> Role.valueOf(role.getName()).getAuthorities())
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
