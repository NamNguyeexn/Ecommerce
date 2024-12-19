package com.prod.services.products;

import com.prod.models.ENUM.Type_Image;
import com.prod.models.products.Image;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IImageService {
    //create
    Image createImage(Image image);
    List<Image> createImages(List<Image> images);
    //get
    Optional<Image> getImageById(int id);
    Optional<Image> getImageByProdIdAndType(int productId, Type_Image typeImage);
    List<Image> getImagesByType(Type_Image typeImage);
    List<Image> getImagesByProductId(int productId);
    Image updateImageByProductId(int id, Image image);
    void deleteImageById(int id);
    void deleteImageByProductId(int id);

}
