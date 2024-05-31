package com.example.course_project_2023.repository.redis.redismodels;

import com.example.course_project_2023.service.dto.UserRegisterDtoRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@RedisHash(value = "userRegistration", timeToLive = 10L)
public class UserRegistration {
    @Id
    private String id;
    @Indexed private String nickname;
    private Long universityId;
    private String fieldOfStudies;
    @Indexed private String email;
    private String password;

    public UserRegistration(UserRegisterDtoRequest dtoRequest){
        this.nickname = dtoRequest.nickname();
        this.universityId = dtoRequest.universityId();
        this.fieldOfStudies = dtoRequest.fieldOfStudies();
        this.email = dtoRequest.email();
        this.password = dtoRequest.password();
    }
}
