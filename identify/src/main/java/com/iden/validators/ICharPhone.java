package com.iden.validators;

import com.iden.validators.vldt.CharPhone;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = CharPhone.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ICharPhone {
    String message() default "Phone only contains numbers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
