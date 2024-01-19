package com.example.course_project_2023.service.vlidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResolutionValidator implements ConstraintValidator<ImageResolution, MultipartFile> {

    private int minWidth;
    private int minHeight;
    private int maxWidth;
    private int maxHeight;

    @Override
    public void initialize(ImageResolution constraintAnnotation) {
        this.minWidth = constraintAnnotation.minWidth();
        this.minHeight = constraintAnnotation.minHeight();
        this.maxWidth = constraintAnnotation.maxWidth();
        this.maxHeight = constraintAnnotation.maxHeight();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            return image != null
                    && image.getWidth() >= minWidth && image.getHeight() >= minHeight
                    && image.getWidth() <= maxWidth && image.getHeight() <= maxHeight
                    && image.getWidth() * 1.0 / image.getHeight() >= 0.9 && image.getWidth() * 1.0 / image.getHeight() <= 1.1;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
