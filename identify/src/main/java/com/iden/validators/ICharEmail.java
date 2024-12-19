package com.iden.validators;

import com.iden.validators.vldt.CharEmail;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CharEmail.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ICharEmail {
    String message() default "Email invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
