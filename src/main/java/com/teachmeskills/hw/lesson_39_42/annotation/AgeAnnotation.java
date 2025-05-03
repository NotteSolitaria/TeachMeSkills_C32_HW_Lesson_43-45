package com.teachmeskills.hw.lesson_39_42.annotation;

import com.teachmeskills.hw.lesson_39_42.service.validator.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = AgeValidator.class)
public @interface AgeAnnotation {
    String message() default "Wrong age";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
