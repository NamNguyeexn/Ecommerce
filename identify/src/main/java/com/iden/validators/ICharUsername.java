package com.iden.validators;

import com.iden.validators.vldt.CharUsername;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CharUsername.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ICharUsername {
    String message() default "Username only contains a-z A-Z 0-9";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
