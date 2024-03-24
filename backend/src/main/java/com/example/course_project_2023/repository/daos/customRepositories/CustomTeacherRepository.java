package com.example.course_project_2023.repository.daos.customRepositories;

import com.example.course_project_2023.repository.model.Teacher;

import java.util.List;
import java.util.Map;

public interface CustomTeacherRepository {
    List<Teacher> findByParams(Map<String, String> params);
    List<Long> findByParamsReturnIds(Map<String, String> params);
}
