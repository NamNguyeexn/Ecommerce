package com.infor.validators;


import com.infor.validators.vldt.ValidName;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidName.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IValidName {
    String message() default "Name invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
