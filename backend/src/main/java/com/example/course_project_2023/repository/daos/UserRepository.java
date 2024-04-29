package com.example.course_project_2023.repository.daos;


import com.example.course_project_2023.repository.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

    @EntityGraph(attributePaths = "university")
    Optional<User> findWithUniversityById(Long id);
}
