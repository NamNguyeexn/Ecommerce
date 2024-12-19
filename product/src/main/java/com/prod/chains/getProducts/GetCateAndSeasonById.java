package com.prod.chains.getProducts;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.details.Category;
import com.prod.models.details.Season;
import com.prod.services.details.ICategoryService;
import com.prod.services.details.ISeasonService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Builder
@AllArgsConstructor
public class GetCateAndSeasonById implements ChainHandler<ProductDTO> {
    private final ISeasonService seasonService;
    private final ICategoryService categoryService;
    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            ProductDTO dto = chainDataDTO.getValue();
            Optional<Category> category = categoryService.getCategoryById(dto.getCategory());
            Optional<Season> season = seasonService.getSeasonById(dto.getSeason_id());
            if (season.isPresent() && category.isPresent()){
                dto.setSeason(season.get().getName() + " " + season.get().getYear());
                dto.setCategoryName(category.get().getName());
                chainDataDTO.setValue(dto).setSuccess(true);
            } else {
                chainDataDTO.setMessage("Khong tim thay cate id hoac season id").setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
}
