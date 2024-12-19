package com.prod.validators.vldt;

import com.prod.facades.DTO.OrderProductDTO;
import com.prod.validators.ProductInOrder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ProductInOrderVldt implements ConstraintValidator<ProductInOrder, List<OrderProductDTO>> {

    @Override
    public boolean isValid(List<OrderProductDTO> dtos,
                           ConstraintValidatorContext constraintValidatorContext) {
        return !dtos.isEmpty();
    }
}