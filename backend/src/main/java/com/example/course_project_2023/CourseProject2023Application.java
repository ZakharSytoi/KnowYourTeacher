package com.example.course_project_2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CourseProject2023Application {

	public static void main(String[] args) {
		SpringApplication.run(CourseProject2023Application.class, args);
	}

}
