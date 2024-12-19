package com.prod.chains.createProduct;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.details.Category;
import com.prod.services.details.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetCategoryById implements ChainHandler<ProductDTO> {
    private final ICategoryService categoryService;

    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            Optional<Category> _cate = categoryService.getCategoryById(
                    chainDataDTO.getValue().getCategory()
            );
            if (_cate.isEmpty()) {
                chainDataDTO.setMessage("Khong tim thay category")
                        .setSuccess(false);
            } else {
                ProductDTO dto = chainDataDTO.getValue();
                dto.setCategoryName(_cate.get().getName());
                chainDataDTO.setValue(dto);
                chainDataDTO.setSuccess(true);
            }
        }
        return new Chain<>(this);
    }
}
