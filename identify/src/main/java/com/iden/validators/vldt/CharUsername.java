package com.iden.validators.vldt;

import com.iden.validators.ICharUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CharUsername implements ConstraintValidator<ICharUsername, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for (Character c : s.toCharArray()){
            if(!Character.isLetterOrDigit(c)) return false;
        }
        return true;
    }
}
