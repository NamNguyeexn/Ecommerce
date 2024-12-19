package com.prod.services.carts;

import com.prod.models.carts.Color_Size_Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IColorSizeProductService {
    //create
    Color_Size_Product createColorSizeProduct(Color_Size_Product colorSizeProduct);
    List<Color_Size_Product> createColorSizeProducts(List<Color_Size_Product> colorSizeProducts);
    //get
    Optional<Color_Size_Product> getColorSizeProductById(int id);
    List<Color_Size_Product> getColorSizeProductsByProductId(int productId);
    List<Color_Size_Product> getColorSizeProductsByColorId(int colorId);
    List<Color_Size_Product> getColorSizeProductsByColorIdAndSizeId(int colorId, int sizeId);
    //delete
    void deleteColorSizeProductById(int id);
    void deleteColorSizeProductsByProductId(int productId);
}
