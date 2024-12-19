package com.iden.validators.vldt;

import com.iden.validators.ICharEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CharEmail implements ConstraintValidator<ICharEmail, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!s.contains("@")) {
            return false;
        } else {
            String[] datas = s.split("@");
            for (String data : datas) {
                if (data.isEmpty()) {
                    return false;
                }
                for (Character character : data.toCharArray()) {
                    if (character.compareTo('.') == 0) continue;
                    if (!Character.isLetterOrDigit(character)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
