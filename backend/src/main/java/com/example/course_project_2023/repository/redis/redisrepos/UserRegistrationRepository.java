package com.example.course_project_2023.repository.redis.redisrepos;

import com.example.course_project_2023.repository.redis.redismodels.UserRegistration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository extends CrudRepository<UserRegistration, String> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}