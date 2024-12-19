package com.iden.validators.vldt;

import com.iden.validators.ICharPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CharPhone implements ConstraintValidator<ICharPhone, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^[0-9]+$";
        return s.matches(regex);
    }
}
