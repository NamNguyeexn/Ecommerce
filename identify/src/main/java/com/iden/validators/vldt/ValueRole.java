package com.iden.validators.vldt;

import com.iden.models.ENUM.ERole;
import com.iden.validators.IValueRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class ValueRole implements ConstraintValidator<IValueRole, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String> list = new ArrayList<>();
        list.add(ERole.ADMIN.toString());
        list.add(ERole.USER.toString());
        return list.contains(s);
    }
}
