package com.prod.chains.createOrder;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CSPCartDTO;
import com.prod.models.ENUM.Type_Image;
import com.prod.models.products.Image;
import com.prod.models.products.Product;
import com.prod.services.products.IImageService;
import com.prod.services.products.IProductService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Builder
public class GetProductById implements ChainHandler<CSPCartDTO> {
    private final IProductService productService;
    private final IImageService imageService;
    @Override
    public Chain<CSPCartDTO> handle(ChainDataDTO<CSPCartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            CSPCartDTO dto = chainDataDTO.getValue();
            Optional<Product> product = productService.getProductById(dto.getProduct_id());
            if (product.isPresent()){
                Optional<Image> image = imageService.getImageByProdIdAndType(product.get().getId(), Type_Image.ANH_NEN);
                dto.setPrice(product.get().getPrice());
                dto.setName(product.get().getTitle());
                image.ifPresent(i -> dto.setImage(i.getSrc()));
                chainDataDTO
                        .setValue(dto)
                        .setSuccess(true);
            } else {
                chainDataDTO
                        .setMessage("Khong tim thay san pham")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
}
