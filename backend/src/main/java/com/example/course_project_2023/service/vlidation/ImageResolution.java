package com.example.course_project_2023.service.vlidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ResolutionValidator.class)
@Documented
public @interface ImageResolution {
    String message() default "image resolution must be from 100x100 to 1000x1000 with aspect ratio ~ 1:1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int minWidth() default 0;
    int minHeight() default 0;
    int maxWidth() default 0;
    int maxHeight() default 0;
}
