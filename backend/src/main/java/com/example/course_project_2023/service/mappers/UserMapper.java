package com.example.course_project_2023.service.mappers;

import com.example.course_project_2023.repository.model.User;
import com.example.course_project_2023.service.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "securityUser.email", target = "email" )
    @Mapping(source = "fieldOfStudies", target = "fieldOfStudies")
    UserResponseDto userToUserResponseDto(User user);
}
