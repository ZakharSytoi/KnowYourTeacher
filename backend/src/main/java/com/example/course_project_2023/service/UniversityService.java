package com.example.course_project_2023.service;

import com.example.course_project_2023.repository.daos.UniversityRepository;
import com.example.course_project_2023.service.dto.UniversityDto;
import com.example.course_project_2023.service.exception.UniversityNotFoundException;
import com.example.course_project_2023.service.mappers.UniversityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;
    private final UniversityMapper universityMapper = UniversityMapper.INSTANCE;

    public List<UniversityDto> getAllUniversities() {
        return universityMapper.universityListToUniversityDtoList(universityRepository.findAll());
    }

    public UniversityDto getUniversityById(Long id) {
        return universityMapper.universityToUniversityDto(universityRepository.findById(id).orElseThrow(
                () -> new UniversityNotFoundException(String.format("University with id %s not found", id)))
        );
    }
}
