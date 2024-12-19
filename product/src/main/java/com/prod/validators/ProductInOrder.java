package com.prod.validators;

import com.prod.validators.vldt.ProductInOrderVldt;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductInOrderVldt.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductInOrder {
    String message() default "Products must not be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
