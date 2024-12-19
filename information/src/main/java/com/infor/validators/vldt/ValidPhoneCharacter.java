package com.infor.validators.vldt;

import com.infor.validators.IValidPhoneCharacter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPhoneCharacter implements ConstraintValidator<IValidPhoneCharacter, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.isEmpty()) return false;
        else {
            for (Character c : s.toCharArray()) {
                if(!Character.isDigit(c)) return false;
            }
        }
        return true;
    }
}
