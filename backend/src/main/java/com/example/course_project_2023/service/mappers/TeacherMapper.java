package com.example.course_project_2023.service.mappers;

import com.example.course_project_2023.repository.model.Teacher;
import com.example.course_project_2023.service.dto.TeacherCardDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);


    @Mappings({
            @Mapping(target = "universityName",
                    expression = "java( teacher.getUniversity().getName() )"),
            @Mapping(target = "teacherPictureUri",
                    expression = "java( org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath()\n" +
                            "                .path(\"/knowyourteacher-api/v1/teachers/pictures/\")\n" +
                            "                .path(teacher.getPicture().getId().toString())\n" +
                            "                .toUriString(); )"),
            @Mapping(target = "teacherReviewsUri",
                    expression = "java( org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath()\n" +
                            "                .path(\"/knowyourteacher-api/v1/teachers/\")\n" +
                            "                .path(teacher.getId().toString()).path(\"/reviews\").toUriString(); )")
    })
    TeacherCardDto cardDtoFromTeacher(Teacher teacher);


}
