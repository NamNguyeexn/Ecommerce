package com.infor.validators.vldt;

import com.infor.validators.IValidName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidName implements ConstraintValidator<IValidName, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.isEmpty()) return false;
        else {
            for (Character c : s.toCharArray()) {
                if(!Character.isLetterOrDigit(c)) return false;
            }
        }
        return true;
    }
}
