package com.prod.validators;

import com.prod.validators.vldt.LabelValidVldt;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LabelValidVldt.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LabelValid {
    String message() default "Label must be valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}