package com.prod.chains.createProduct;

import com.prod.facades.DTO.ColorSizeQuantityDTO;
import com.prod.facades.DTO.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractProduct {
    public int getTotalQuantity(ProductDTO productDTO) {
        int sum = 0;
        for (ColorSizeQuantityDTO _csq : productDTO.getCsq()){
            sum += _csq.getQuantity();
        }
        return sum;
    }
}
