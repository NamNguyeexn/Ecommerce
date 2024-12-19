package com.prod.chains.getProducts;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ImageDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.products.Image;
import com.prod.services.products.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class GetImageByProdId implements ChainHandler<ProductDTO> {
    private final IImageService imageService;
    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            List<Image> images = imageService.getImagesByProductId(
                    chainDataDTO.getValue().getProductId()
            );
            if (!images.isEmpty()){
                ProductDTO dto = chainDataDTO.getValue();
                changeData(dto, images);
                chainDataDTO.setValue(dto)
                        .setSuccess(true);
            } else {
                chainDataDTO.setMessage("Khong tim thay danh sach anh cua san pham")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
    private void changeData(ProductDTO productDTO, List<Image> images){
        List<ImageDTO> imageDTOS = new ArrayList<>();
        images.forEach(image -> {
            imageDTOS.add(ImageDTO.builder()
                            .url(image.getSrc())
                            .name(image.getName())
                            .typeImage(image.getType())
                    .build());
        });
        productDTO.setImages(imageDTOS);
    }
}
