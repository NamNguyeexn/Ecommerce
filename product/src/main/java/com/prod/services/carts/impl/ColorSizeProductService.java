package com.prod.services.carts.impl;

import com.prod.JPARepositories.carts.ColorSizeProductRepository;
import com.prod.models.carts.Color_Size_Product;
import com.prod.services.carts.IColorSizeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.carts.ColorSizeProductRepository.Specs.*;

@Service
@Primary
public class ColorSizeProductService implements IColorSizeProductService {
    @Autowired
    private ColorSizeProductRepository repository;
    @Override
    public Color_Size_Product createColorSizeProduct(Color_Size_Product colorSizeProduct) {
        return repository.save(colorSizeProduct);
    }

    @Override
    public List<Color_Size_Product> createColorSizeProducts(List<Color_Size_Product> colorSizeProducts) {
        List<Color_Size_Product> products = new ArrayList<>();
        for (Color_Size_Product colorSizeProduct : colorSizeProducts) {
            products.add(createColorSizeProduct(colorSizeProduct));
        }
        return products;
    }

    @Override
    public Optional<Color_Size_Product> getColorSizeProductById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Color_Size_Product> getColorSizeProductsByProductId(int productId) {
        return repository.findAll(byProductId(productId));
    }

    @Override
    public List<Color_Size_Product> getColorSizeProductsByColorId(int colorId) {
        return repository.findAll(byColorId(colorId));
    }

    @Override
    public List<Color_Size_Product> getColorSizeProductsByColorIdAndSizeId(int colorId, int sizeId) {
        return repository.findAll(byColorIdAndSizeId(colorId, sizeId));
    }

    @Override
    public void deleteColorSizeProductById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteColorSizeProductsByProductId(int productId) {
        repository.delete(byProductId(productId));
    }
}
