package com.example.course_project_2023.service.mappers;

import com.example.course_project_2023.repository.model.University;
import com.example.course_project_2023.service.dto.UniversityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UniversityMapper {
    UniversityMapper INSTANCE = Mappers.getMapper(UniversityMapper.class);

    UniversityDto universityToUniversityDto(University university);
    List<UniversityDto> universityListToUniversityDtoList(List<University> universities);
}
