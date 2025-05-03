package com.teachmeskills.hw.lesson_39_42.service.validator;

import com.teachmeskills.hw.lesson_39_42.annotation.AgeAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<AgeAnnotation, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value >= 18 && value <= 120;
    }
}
