package com.prod.chains.updateCart;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CartDTO;
import com.prod.models.ENUM.Type_Image;
import com.prod.models.products.Image;
import com.prod.services.products.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetFirstImageByProdId implements ChainHandler<CartDTO> {
    private final IImageService imageService;
    @Override
    public Chain<CartDTO> handle(ChainDataDTO<CartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            CartDTO dto = chainDataDTO.getValue();
            Optional<Image> image = imageService.getImageByProdIdAndType(dto.getProductId(), Type_Image.ANH_NEN);
            if (image.isPresent()) {
                dto.setImageSrc(image.get().getSrc());
                chainDataDTO.setValue(dto).setSuccess(true);
            } else {
                chainDataDTO.setMessage("Khong tim thay anh nen").setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
}
