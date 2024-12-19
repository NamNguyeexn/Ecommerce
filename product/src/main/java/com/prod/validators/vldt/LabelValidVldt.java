package com.prod.validators.vldt;

import com.prod.JPARepositories.products.LabelRepository;
import com.prod.models.products.Label;
import com.prod.validators.LabelValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class LabelValidVldt implements ConstraintValidator<LabelValid, List<String>> {
    @Autowired
    private LabelRepository labelRepository;
    @Override
    public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        List<String> labelsNames = labelRepository.findAll().stream().map(Label::getName).toList();
        for (String labelName : strings) {
            if (!labelsNames.contains(labelName)) {
                return false;
            }
        }
        return true;
    }
}
