package com.iden.validators;

import com.iden.validators.vldt.ValueRole;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValueRole.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IValueRole {
    String message() default "Role invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
