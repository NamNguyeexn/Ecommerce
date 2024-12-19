package com.infor.validators;

import com.infor.validators.vldt.ValidPhoneCharacter;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPhoneCharacter.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IValidPhoneCharacter {
    String message() default "Phone number invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
