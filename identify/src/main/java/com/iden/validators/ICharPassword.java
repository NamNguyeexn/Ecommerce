package com.iden.validators;

import com.iden.validators.vldt.CharPassword;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CharPassword.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ICharPassword {
    String message() default "Password only contains a-z A-Z 0-9";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}



