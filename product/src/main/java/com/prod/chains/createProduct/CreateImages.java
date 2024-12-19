package com.prod.chains.createProduct;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ImageDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.products.Image;
import com.prod.services.products.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CreateImages implements ChainHandler<ProductDTO> {
    private final IImageService imageService;

    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            ProductDTO dto = chainDataDTO.getValue();
            List<Image> images = imageService.getImagesByProductId(dto.getProductId());
            List<ImageDTO> res = new ArrayList<>();
            if (!images.isEmpty()) {
                imageService.deleteImageByProductId(dto.getProductId());
            }
            chainDataDTO.getValue().getImages().forEach(
                    i -> {
                        res.add(createImage(i, dto.getProductId()));
                    }
            );
            chainDataDTO.setSuccess(true);
//            else {
//                for (Image image : images) {
//                    for (ImageDTO imageDTO : dto.getImages()) {
//                        if (imageDTO.getName().equals(image.getName()) &&
//                                imageDTO.getTypeImage().equals(image.getType())) {
//                            res.add(updateImage(image, imageDTO.getUrl()));
//                        } else {
//                            res.add(createImage(imageDTO, dto.getProductId()));
//                        }
//                        images.remove(image);
//                        dto.getImages().remove(imageDTO);
//                    }
//                }
//            }
            if (!res.isEmpty()) {
                dto.setImages(res);
                chainDataDTO.setValue(dto).setSuccess(true);
            } else {
                chainDataDTO.setMessage("Khong the them hinh anh, danh sach trong")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }

    private ImageDTO createImage(ImageDTO dto, int productId){
        Image newImage = imageService.createImage(
                Image.builder()
                        .name(dto.getName())
                        .src(dto.getUrl())
                        .type(dto.getTypeImage())
                        .product_id(productId)
                        .build()
        );
        return ImageDTO.builder()
                .url(newImage.getSrc())
                .name(newImage.getName())
                .typeImage(newImage.getType())
                .build();
    }

//    private ImageDTO updateImage(Image image, String src){
//        image.setSrc(src);
//        image.setUpdate_at(LocalDateTime.now());
//        Image newImage = imageService.createImage(image);
//        return ImageDTO.builder()
//                .url(newImage.getSrc())
//                .name(newImage.getName())
//                .typeImage(newImage.getType())
//                .build();
//    }
}
